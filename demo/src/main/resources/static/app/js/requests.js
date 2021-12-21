var token = $('#_csrf').attr('content');
var header = $('#_csrf_header').attr('content');

//add tasklist
const user_id = 1;

$("#send").click(function (e) {
    e.preventDefault();
    const obj = { name: "myfirsttasklist" };
    $.ajax({

        method: "POST",
        url: "/api/tasklist/" + user_id,
        contentType: 'application/json',
        headers: { 'X-CSRF-Token': token },
        data: JSON.stringify(obj)
    })
        .done(function (msg) {
            alert("Data Saved: " + msg);
        })
        .fail(function (msg) {
            alert("failed to save " + msg);
        });

});
