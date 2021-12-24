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

//add task
function addTask(tasklists, tasklistIndex, taskName) {
    //request

    //on success
    let task = {
        name: taskName,
        time: null,
        completed: false
    };
    tasklists[tasklistIndex].tasks.push(task);
    console.log(tasklists);
    return tasklists;
}


//delete task
function deleteTask(tasklists, tasklistIndex, taskIndex) {
    //request
    //on success
    tasklists[tasklistIndex].tasks.splice(taskIndex, 1);
    return tasklists;
}

//complete task
function completeTask(tasklists, tasklistIndex, taskIndex, isCompleted) {
    //request
    //on success
    tasklists[tasklistIndex].tasks[taskIndex].completed = isCompleted;
    return tasklists;
}

//add tasklist
function addTasklist(tasklists, tasklistName) {
    //request
    //on success
    newTasklist = {
        tasks: [],
        name: tasklistName
    };
    tasklists.push(newTasklist);
    return tasklists;
}