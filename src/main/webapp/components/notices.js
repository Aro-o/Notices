$(document).ready(function() {
    if ($('#alertSuccess').text().trim() == "") {
        $('#alertSuccess').hide();
    }

    $('#alertError').hide();
})

$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidNoticeIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "NoticesAPI", 
 type : type, 
 data : $("#formNotice").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onNoticeSaveComplete(response.responseText, status); 
 } 
 }); 
});



function onNoticeSaveComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divNoticesGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 } 
$("#hidNoticeIDSave").val(""); 
 $("#formNotice")[0].reset(); 
}

//Update button
$(document).on("click", ".btnUpdate", function(event) 
{ 
$("#hidNoticeIDSave").val($(this).data("id")); 
 $("#topic").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#areasAffected").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#date").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#details").val($(this).closest("tr").find('td:eq(3)').text()); 
});




$(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "NoticesAPI", 
 type : "DELETE", 
 data : "id=" + $(this).data("id"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemDeleteComplete(response.responseText, status); 
 } 
 }); 
});

function onNoticeDeleteComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divNoticesGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}




// CLIENT-MODEL================================================================ 
function validateNoticeForm() { 
    // TOPIC
    if ($("#topic").val().trim() == "") 
    { 
        return "Insert topic."; 
    } 
    
    // AREAS AFFECTED 
    if ($("#areasAffected").val().trim() == "") 
    { 
        return "Insert Areas Affected."; 
    } 
    
    // DATE
    if ($("#date").val().trim() == "") 
    { 
        return "Insert date."; 
    } 
    
    
    // DETAILS
    if ($("#details").val().trim() == "") 
    { 
        return "Insert details."; 
    } 
    
  /*  // PRICE------------------------------- 
    if ($("#date").val().trim() == "") 
    { 
        return "Insert date."; 
    } 
    
    // is numerical value 
    var tmpPrice = $("#itemPrice").val().trim(); 
    if (!$.isNumeric(tmpPrice)) 
    { 
        return "Insert a numerical value for Item Price."; 
    } 
    
    // convert to decimal price 
    $("#itemPrice").val(parseFloat(tmpPrice).toFixed(2)); 
    
    // DESCRIPTION------------------------ 
    if ($("#itemDesc").val().trim() == "") 
    { 
        return "Insert Item Description."; 
    } 
    */
    return true; 
} 
