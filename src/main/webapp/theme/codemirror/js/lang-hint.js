(function () {
  var Pos = CodeMirror.Pos;

  function forEach(arr, f) {
    for (var i = 0, e = arr.length; i < e; ++i) f(arr[i]);
  }

  function arrayContains(arr, item) {
    if (!Array.prototype.indexOf) {
      var i = arr.length;
      while (i--) {
        if (arr[i] === item) {
          return true;
        }
      }
      return false;
    }
    return arr.indexOf(item) != -1;
  }

  function scriptHint(editor, keywords, getToken, options) {
    // Find the token at the cursor
    var cur = editor.getCursor(), token = getToken(editor, cur), tprop = token;
    token.state = CodeMirror.innerMode(editor.getMode(), token.state).state;

    // If it's not a 'word-style' token, ignore the token.
    if (!/^[\w$_]*$/.test(token.string)) {
      token = tprop = {start: cur.ch, end: cur.ch, string: "", state: token.state,
                       type: token.string == "." ? "property" : null};
    } else {
    	// check if current token is clinical object attribute
    	var len = token.string.length;
    	var dot = getToken(editor, Pos(cur.line, cur.ch - len));
    	if(dot.string == "."){
    		var obj = getToken(editor, Pos(cur.line, cur.ch - len -1));
        	tprop = {start: cur.ch, end: cur.ch, string: obj.string, state: token.state,
                    type:  "attribute"};
        	if (!context) var context = [];
            context.push(tprop);
    	}
    	
    	
    }
    // If it is a property, find out what it is a property of.
    while (tprop.type == "property") {
      tprop = getToken(editor, Pos(cur.line, tprop.start));
      if (tprop.string != ".") return;
      tprop = getToken(editor, Pos(cur.line, tprop.start));
      if (!context) var context = [];
      context.push(tprop);
    }
    return {list: getCompletions(token, context, keywords, options),
            from: Pos(cur.line, token.start),
            to: Pos(cur.line, token.end)};
  }
  

  function javascriptHint(editor, options) {
    return scriptHint(editor, keywords,
                      function (e, cur) {return e.getTokenAt(cur);},
                      options);
  };
  CodeMirror.javascriptHint = javascriptHint; // deprecated
  CodeMirror.registerHelper("hint", "lang", javascriptHint);

  
  var keywords = ("library version QUICK using include called identifier public private parameter default codesystem valueset codesystems List Interval " +
  		"Tuple context define function with in from where all distinct sort by asc ascending desc descending is not null true false as cast exists " +
  		"properly between and contains or xor union intersect except year month day hour minute second millisecond date time timezone years months days " +
  		"hours minutes seconds milliseconds start end duration of width successor predecessor singleton minimum maximum if then else case collapse expand " +
  		"when starts ends occurs same before after overlaps Code Concept display").split(" ");
  keywords.push('such that');
  keywords.push('or before');
  keywords.push('or after');
  keywords.push('or more');
  keywords.push('or less');

  var clinicalObjects = ("AllergyIntolerance BodySite Communication CommunicationRequest Condition Device DeviceUseRequest DeviceUseStatement " +
          "DiagnosticOrder DiagnosticReport Encounter FamilyMemberHistory Flag Goal ImagingStudy Immunization ImmunizationRecommendation Location Medication " +
          "MedicationAdministration MedicationDispense MedicationPrescription MedicationStatement Observation Organization Patient Practitioner Procedure " +
          "ProcedureRequest ReferralRequest RelatedPerson Specimen Substance").split(" ");  
  var attribs = ("").split(" ");
                      
  var clAttribs = new Array(         
          {name: "AllergyIntolerance", attribs: new Array("category", "comment", "criticality", "event", "event.certainty", "event.comment", "event.description", "event.duration", "event.exposureRoute", "event.manifestation", "event.onset", "event.severity", "event.substance", "identifier", "lastOccurence", "patient", "reasonRefuted", "recordedDate", "recorder", "reporter", "resolutionAge", "status", "substance", "type")}, 
          {name: "Appointment", attribs: new Array("identifier", "status", "serviceCategory", "serviceType", "specialty", "appointmentType", "reason", "priority", "description", "start", "end", "minutesDuration", "slot", "created", "comment")},
          {name: "CommunicationRequest", attribs: new Array("category", "encounter", "identifier", "medium", "orderedBy", "orderedOn", "payload", "payload.content[]", "priority", "reason", "reasonRejected", "recipient", "requester", "scheduledTime", "sender", "status", "subject")},
          {name: "Condition", attribs: new Array("abatement[]", "asserter", "category", "clinicalStatus", "code", "criticality", "dateAsserted", "dueTo", "dueTo.code", "dueTo.target", "encounter", "evidence", "evidence.code", "evidence.detail", "identifier", "location", "location.site[]", "notes", "occurredFollowing", "occurredFollowing.code", "occurredFollowing.target", "onset[]", "patient", "severity", "stage", "stage.assessment", "stage.summary")},
          {name: "DiagnosticOrder", attribs: new Array("clinicalNotes", "encounter", "event", "event.actor", "event.dateTime", "event.description", "event.status", "identifier", "item", "item.bodySite[]", "item.code", "item.event", "item.precondition", "item.specimen", "item.status", "orderer", "priority", "reason", "reasonRejected", "specimen", "status", "subject", "supportingInformation")},
          {name: "DocumentReference" , attribs: new Array("masterIdentifier", "identifier", "subject", "type", "class", "author", "custodian", "authenticator", "created", "indexed", "status", "docStatus", "description", "securityLabel")}, 
          {name: "Encounter", attribs: new Array("class", "episodeOfCare", "extension.extension.url", "extension.extension.valueReference", "fulfills", "hospitalization", "hospitalization.admitSource", "hospitalization.destination", "hospitalization.dietPreference", "hospitalization.dischargeDiagnosis", "hospitalization.dischargeDisposition", "hospitalization.origin", "hospitalization.preAdmissionIdentifier", "hospitalization.reAdmission", "hospitalization.specialArrangement", "hospitalization.specialCourtesy", "identifier", "incomingReferralRequest", "indication", "length", "location", "location.location", "location.period", "location.status", "participant", "participant.individual", "participant.period", "participant.type", "partOf", "patient", "period", "priority", "reason", "reasonCancelled", "relatedCondition", "relatedCondition.condition", "relatedCondition.conditionRole", "serviceProvider", "status", "statusHistory", "statusHistory.period", "statusHistory.status", "type")}, 
          {name: "Flag" , attribs: new Array("identifier", "category", "status", "period", "subject", "encounter", "author", "code")}, 
          {name: "Goal" , attribs: new Array("identifier", "subject", "start", "target", "category", "description", "status", "statusDate", "statusReason", "expressedBy", "priority", "addresses", "note")}, 
          {name: "Immunization" , attribs: new Array("identifier", "status", "date", "vaccineCode", "patient", "wasNotGiven", "reported", "performer", "requester", "encounter", "manufacturer", "location", "lotNumber", "expirationDate", "site", "route", "doseQuantity", "note")}, 
          {name: "ImmunizationRecommedation" , attribs: new Array("patient", "recommendation.date ", "recommendation.doseNumber", "recommendation.vaccineType")}, 
          {name: "Medication" , attribs: new Array("code", "isBrand", "manufacturer")}, 
          {name: "MedicationDispense" , attribs: new Array("identifier", "status", "medication", "patient", "dispenser", "dispensingOrganization", "authorizingPrescription", "type", "quantity", "daysSupply", "whenPrepared", "whenHandedOver", "destination", "receiver", "note")}, 
          {name: "MedicationOrder" , attribs: new Array("identifier", "status", "medication", "patient", "encounter", "dateWritten", "prescriber", "reasonCode", "reasonReference", "note", "category", "priorPrescription")}, 
          {name: "MedicationStatement" , attribs: new Array("identifier", "status", "medication", "patient", "effective", "informationSource", "supportingInformation", "dateAsserted", "notTaken", "reasonNotTaken", "reasonForUseCode", "reasonForUseReference", "note", "category")}, 
          {name: "Observation" , attribs: new Array("identifier", "status", "category", "code", "subject", "encounter", "effective", "issued", "performer", "value", "dataAbsentReason", "interpretation", "comment", "bodySite", "method", "specimen", "device")}, 
          {name: "Patient" , attribs: new Array("identifier", "active", "name", "telecom", "gender", "birthDate", "deceased", "address", "maritalStatus", "multipleBirth", "photo", "generalPractitioner", "managingOrganization")}, 
          {name: "Practioner" , attribs: new Array("identifier", "active", "name", "telecom", "address", "gender", "birthDate", "photo", "communication")}, 
          {name: "Procedure" , attribs: new Array("identifier", "subject", "status", "category", "code", "notPerformed", "reasonNotPerformed", "bodySite", "reasonReference", "reasonCode", "performed", "encounter", "location", "outcome", "report", "complication", "followUp", "request", "notes", "usedReference", "usedCode", "component")}, 
          {name: "ProcedureRequest" , attribs: new Array("identifier", "subject", "code", "bodySite", "reason", "scheduled", "encounter", "performer", "status", "notes", "asNeeded", "orderedOn", "orderer", "priority")}, 
          {name: "ReferralRequest" , attribs: new Array("identifier", "basedOn", "parent", "status", "category", "type", "priority", "patient", "context", "fulfillmentTime", "authored", "requester", "specialty", "recipient", "reason", "description", "serviceRequested", "supportingInformation")}
          );

  function getCompletions(token, context, keywords, options) {
    var found = [], start = token.string;
    function maybeAdd(str) {
      if (str.indexOf(start) == 0 && !arrayContains(found, str)) found.push(str);
    }
    function gatherCompletions(obj) {
    	for(var i = 0;i < clAttribs.length;i++){
    		if(obj == clAttribs[i].name){
    			forEach(clAttribs[i].attribs, maybeAdd);
    			break;
    		}
    	}
    }

    if (context && context.length) {
      // If this is a property, see if it belongs to some clinical object
      var obj = context.pop();
     gatherCompletions(obj.string);
    } else {
      forEach(keywords, maybeAdd);
      forEach(clinicalObjects, maybeAdd);
    }
    return found;
  }
})();

