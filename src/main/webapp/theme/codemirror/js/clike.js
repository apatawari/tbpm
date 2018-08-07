CodeMirror.defineMode("clike", function(config, parserConfig) {
  var indentUnit = config.indentUnit,
      statementIndentUnit = parserConfig.statementIndentUnit || indentUnit,
      dontAlignCalls = parserConfig.dontAlignCalls,
      keywords = parserConfig.keywords || {},
      builtin = parserConfig.builtin || {},
      clinicalObjects = parserConfig.clinicalObjects || {},
      time = parserConfig.time || {},
      attributes = parserConfig.attributes || {},
      atoms = parserConfig.atoms || {},
      hooks = parserConfig.hooks || {},
      multiLineStrings = parserConfig.multiLineStrings;
  var isOperatorChar = /[+\-*&%=<>!?|\/]/;

  var curPunc;

  function tokenBase(stream, state) {
    var ch = stream.next();
    if (hooks[ch]) {
      var result = hooks[ch](stream, state);
      if (result !== false) return result;
    }
    if (ch == '"' || ch == "'") {
      state.tokenize = tokenString(ch);
      return state.tokenize(stream, state);
    }
    if (/[\[\]{}\(\),;\:\.]/.test(ch)) {
      curPunc = ch;
      return null;
    }
    if (/\d/.test(ch)) {
      stream.eatWhile(/[\w\.]/);
      return "number";
    }
    if (ch == "/") {
      if (stream.eat("*")) {
        state.tokenize = tokenComment;
        return tokenComment(stream, state);
      }
      if (stream.eat("/")) {
        stream.skipToEnd();
        return "comment";
      }
    }
    if (isOperatorChar.test(ch)) {
      stream.eatWhile(isOperatorChar);
      return "operator";
    }
    stream.eatWhile(/[\w\$_]/);
    var cur = stream.current();
    if (clinicalObjects.propertyIsEnumerable(cur)) {
        return "clinicalObject";
      }
    if (attributes.propertyIsEnumerable(cur)){
    	return "attribute";
    }
    // turn to lowercase, since we are case-insensitive for the rest of tokens
    cur = cur.toLowerCase();
    if (keywords.propertyIsEnumerable(cur)) {
      return "keyword";
    }
   
    if (atoms.propertyIsEnumerable(cur)) return "atom";
    if (time.propertyIsEnumerable(cur)) return "time";
    if (builtin.propertyIsEnumerable(cur)) return "builtin";
    return "variable";
  }

  function tokenString(quote) {
    return function(stream, state) {
      var escaped = false, next, end = false;
      while ((next = stream.next()) != null) {
        if (next == quote && !escaped) {end = true; break;}
        escaped = !escaped && next == "\\";
      }
      if (end || !(escaped || multiLineStrings))
        state.tokenize = null;
      return "string";
    };
  }

  function tokenComment(stream, state) {
    var maybeEnd = false, ch;
    while (ch = stream.next()) {
      if (ch == "/" && maybeEnd) {
        state.tokenize = null;
        break;
      }
      maybeEnd = (ch == "*");
    }
    return "comment";
  }

  function Context(indented, column, type, align, prev) {
    this.indented = indented;
    this.column = column;
    this.type = type;
    this.align = align;
    this.prev = prev;
  }
  function pushContext(state, col, type) {
    var indent = state.indented;
    if (state.context && state.context.type == "statement")
      indent = state.context.indented;
    return state.context = new Context(indent, col, type, null, state.context);
  }
  function popContext(state) {
    var t = state.context.type;
    if (t == ")" || t == "]" || t == "}")
      state.indented = state.context.indented;
    return state.context = state.context.prev;
  }

  // Interface

  return {
    startState: function(basecolumn) {
      return {
        tokenize: null,
        context: new Context((basecolumn || 0) - indentUnit, 0, "top", false),
        indented: 0,
        startOfLine: true
      };
    },

    token: function(stream, state) {
      var ctx = state.context;
      if (stream.sol()) {
        if (ctx.align == null) ctx.align = false;
        state.indented = stream.indentation();
        state.startOfLine = true;
      }
      if (stream.eatSpace()) return null;
      curPunc = null;
      var style = (state.tokenize || tokenBase)(stream, state);
      if (style == "comment" || style == "meta") return style;
      if (ctx.align == null) ctx.align = true;

      if ((curPunc == ";" || curPunc == ":" || curPunc == ",") && ctx.type == "statement") popContext(state);
      else if (curPunc == "{") pushContext(state, stream.column(), "}");
      else if (curPunc == "[") pushContext(state, stream.column(), "]");
      else if (curPunc == "(") pushContext(state, stream.column(), ")");
      else if (curPunc == "}") {
        while (ctx.type == "statement") ctx = popContext(state);
        if (ctx.type == "}") ctx = popContext(state);
        while (ctx.type == "statement") ctx = popContext(state);
      }
      else if (curPunc == ctx.type) popContext(state);
      else if (((ctx.type == "}" || ctx.type == "top") && curPunc != ';') || (ctx.type == "statement" && curPunc == "newstatement"))
        pushContext(state, stream.column(), "statement");
      state.startOfLine = false;
      return style;
    },

    indent: function(state, textAfter) {
      if (state.tokenize != tokenBase && state.tokenize != null) return CodeMirror.Pass;
      var ctx = state.context, firstChar = textAfter && textAfter.charAt(0);
      if (ctx.type == "statement" && firstChar == "}") ctx = ctx.prev;
      var closing = firstChar == ctx.type;
      if (ctx.type == "statement") return ctx.indented + (firstChar == "{" ? 0 : statementIndentUnit);
      else if (ctx.align && (!dontAlignCalls || ctx.type != ")")) return ctx.column + (closing ? 0 : 1);
      else if (ctx.type == ")" && !closing) return ctx.indented + statementIndentUnit;
      else return ctx.indented + (closing ? 0 : indentUnit);
    },

    electricChars: "{}",
    blockCommentStart: "/*",
    blockCommentEnd: "*/",
    lineComment: "//",
    fold: "brace"
  };
});

(function() {
  function words(str) {
    var obj = {}, words = str.split(" ");
    for (var i = 0; i < words.length; ++i) obj[words[i]] = true;
    return obj;
  }
 
  CodeMirror.defineMIME("text/x-csharp", {
    name: "clike",
    keywords: words("library version QUICK using include called identifier public private parameter default codesystem valueset codesystems List Interval " +
  		"Tuple context define function with in from where all distinct sort by asc ascending desc descending is not null true false as cast exists " +
  		"properly between and contains or xor union intersect except year month day hour minute second millisecond date time timezone years months days " +
  		"hours minutes seconds milliseconds start end duration of width successor predecessor singleton minimum maximum if then else case collapse expand " +
  		"when starts ends occurs same before after overlaps Code Concept display"),
    attributes: words("cause cause.certainty cause.item clinicalStudy didNotOccur period severity type author code created identifier subject " +
			  "category comment criticality event event.certainty event.comment event.description event.duration event.exposureRoute event.manifestation event.onset event.severity event.substance identifier lastOccurence patient reasonRefuted recordedDate recorder reporter resolutionAge status substance type " +
			  "code description identifier image modifier patient" +
			  "category encounter identifier medium payload payload.content[] reason reasonNotPerformed received recipient sender sent status subject " +
			  "category encounter identifier medium orderedBy orderedOn payload payload.content[] priority reason reasonRejected recipient requester scheduledTime sender status subject " +
			  "abatement[] asserter category clinicalStatus code criticality dateAsserted dueTo dueTo.code dueTo.target encounter evidence evidence.code evidence.detail identifier location location.site[] notes occurredFollowing occurredFollowing.code occurredFollowing.target onset[] patient severity stage stage.assessment stage.summary " +
			  "contact expiry identifier location lotNumber manufactureDate manufacturer model owner patient status type udi url version " +
			  "bodySite[] device encounter identifier indication notes orderedOn priority prnReason reasonRejected recordedOn status subject timing[] " +
			  "bodySite[] device identifier indication notes recordedOn subject timing[] whenUsed " +         
			  "clinicalNotes encounter event event.actor event.dateTime event.description event.status identifier item item.bodySite[] item.code item.event item.precondition item.specimen item.status orderer priority reason reasonRejected specimen status subject supportingInformation " +
			  "codedDiagnosis conclusion diagnostic[] encounter extension.valueReference identifier image image.comment image.link imagingStudy issued locationPerformed name performer presentedForm requestDetail result serviceCategory specimen status subject " +          
			  "class episodeOfCare extension.extension.url extension.extension.valueReference fulfills hospitalization hospitalization.admitSource hospitalization.destination hospitalization.dietPreference hospitalization.dischargeDiagnosis hospitalization.dischargeDisposition hospitalization.origin hospitalization.preAdmissionIdentifier hospitalization.reAdmission hospitalization.specialArrangement hospitalization.specialCourtesy identifier incomingReferralRequest indication length location location.location location.period location.status participant participant.individual participant.period participant.type partOf patient period priority reason reasonCancelled relatedCondition relatedCondition.condition relatedCondition.conditionRole serviceProvider status statusHistory statusHistory.period statusHistory.status type "),			  
    clinicalObjects: words("AllergyIntolerance Appointment CommunicationRequest Condition DiagnosticOrder DocumentReference Encounter Flag Goal Immunization " +
    			"ImmunizationRecommedation Medication MedicationDispense MedicationOrder MedicationStatement Observation Patient Practioner Procedure " +
    			"ProcedureRequest ReferralRequest"),
    atoms: words("true false null and or not"),
    builtin: words("int float"),
    time: words("minutes hours days weeks months years january february march april may june " +
    			"july august september october november december")
  });
 
}());
