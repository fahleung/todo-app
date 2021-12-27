var token = $('#_csrf').attr('content');
var header = $('#_csrf_header').attr('content');

//add tasklist

//add task
function addTask(tasklistName, taskName) {
    let name = firstCapitalLetter(taskName);
    let json = {
        tasklistname: tasklistName,
        taskname: name
    }

    $.ajax({
        method: "POST",
        url: "/api/task/" + user_id,
        contentType: 'application/json',
        headers: { 'X-CSRF-Token': token },
        data: JSON.stringify(json)
    })
        .done(function (msg) {
            let task = {
                name: name,
                time: null,
                completed: false
            };
            let tasklistRow = getTasklistByName(tasklistName, tasklists);
            tasklists[tasklistRow.index].tasks.push(task);
            if (tasklistRow !== null) {
                //add item to this tasklist with id x_y
                $("#" + tasklistName + "_list").append(createTask(tasklistRow, name));
                let taskIndex = tasklistRow.tasklist.tasks.length - 1;
                let check = $("#check_id_" + tasklistRow.tasklist.name + '_' + taskIndex);
                let cross = $("#cross_id_" + tasklistRow.tasklist.name + '_' + taskIndex);
                addListListener(tasklistRow, check, cross, taskIndex);
                updateItemsLeft(tasklistRow.tasklist.tasks.length);
            }
            else {
                setAlert("Creating item error");
            }
        })
        .fail(function (msg) {
            setAlert(msg.responseText);
        });
}

//delete task
function deleteTask(tasklistIndex, taskIndex) {
    let json = {
        taskname: tasklists[tasklistIndex].tasks[taskIndex].name,
        tasklistname: selectedTasklist
    }

    $.ajax({
        method: "DELETE",
        url: "/api/task/" + user_id,
        contentType: 'application/json',
        headers: { 'X-CSRF-Token': token },
        data: JSON.stringify(json)
    })
        .done(function (msg) {
            //tasklists[tasklistIndex].tasks.splice(taskIndex, 1);
            updateItemsLeft(tasklists[tasklistIndex].tasks.length);
        })
        .fail(function (msg) {
            setAlert(msg.responseText);
        });
}

//complete task
function completeTask(tasklistIndex, taskIndex) {
    let isCompleted = tasklists[tasklistIndex].tasks[taskIndex].completed;
    //request
    let json = {
        taskname: tasklists[tasklistIndex].tasks[taskIndex].name,
        tasklistname: selectedTasklist,
        isCompleted: isCompleted
    }

    $.ajax({
        method: "PUT",
        url: "/api/task/" + user_id,
        contentType: 'application/json',
        headers: { 'X-CSRF-Token': token },
        data: JSON.stringify(json)
    })
        .done(function (msg) {
            //tasklists[tasklistIndex].tasks.splice(taskIndex, 1);
            updateItemsLeft(tasklists[tasklistIndex].tasks.length);
            tasklists[tasklistIndex].tasks[taskIndex].completed = isCompleted;
        })
        .fail(function (msg) {
            setAlert(msg.responseText);
        });
    //on success
}

//add tasklist
function addTasklist(tasklistName) {
    let name = firstCapitalLetter(tasklistName);
    let json = {
        name: name
    }

    $.ajax({
        method: "POST",
        url: "/api/tasklist/" + user_id,
        contentType: 'application/json',
        headers: { 'X-CSRF-Token': token },
        data: JSON.stringify(json)
    })
        .done(function (msg) {
            let newTasklist = {
                tasks: [],
                name: name
            };
            //if tasklists array already exist
            if (tasklists.length !== 0) {
                //get last tasklist name btn to append to
                let lastTasklistName = tasklists[tasklists.length - 1].name;
                htmlElements = createTasklist(name);
                $("#" + lastTasklistName + "_btn").after(htmlElements.button);
                $("#" + lastTasklistName).after(htmlElements.div);
            }
            else {
                htmlElements = createTasklist(name);
                tablinks_add.before(htmlElements.button);
                $("#tabs").after(htmlElements.div);
            }
            tasklists.push(newTasklist);
            $("#" + name + "_btn").on('click', function (e) {
                openTasklist(e, name);
            });
            $("#" + name + "_btn").click();
        })
        .fail(function (msg) {
            setAlert(msg.responseText);
        });
}