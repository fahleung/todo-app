$(document).ready(function () {
    console.log(tasklists);
    //init listeners for each task
    tasklists.forEach(function (tasklist, tasklistIndex) {
        tasklist.tasks.forEach(function (task, taskIndex) {
            let check = $("#check_id_" + tasklist.name + '_' + taskIndex);
            let cross = $("#cross_id_" + tasklist.name + '_' + taskIndex);
            tasklistRow = {
                tasklist: tasklist,
                index: tasklistIndex
            }
            addListListener(tasklistRow, check, cross, taskIndex);
            updateItemsLeft(tasklist.tasks.length - 1);
            if (task.completed) {
                check.click();
            }
        });
    });

    //open first tab by default
    if (tasklists.length !== 0) {
        $("#" + tasklists[0].name + "_btn").click();
    }
})

var typing;
//input
add_todo.addEventListener("focusin", function () {
    typing = setInterval(typingTimer, 300);
    let dots = 0;
    let placeholder = 'Currently typing';
    function typingTimer() {
        let placeholdertoshow = placeholder;
        dots++;
        for (let i = 0; i < dots; i++) {
            placeholdertoshow = placeholdertoshow + '.';
        }
        if (dots == 3) {
            dots = 0;
        }
        add_todo.placeholder = placeholdertoshow;
    }
});

add_todo.addEventListener("focusout", function () {
    clearInterval(typing);
    add_todo.placeholder = 'Create a new todo...';
});

add_todo.addEventListener('keypress', function (e) {
    //create new item
    if (e.key === 'Enter' && add_todo.value != "") {
        let item_string = add_todo.value;
        add_todo.value = "";

        if (tasklists.length !== 0 && selectedTasklist != null) {
            let tasklistRow = getTasklistByName(selectedTasklist, tasklists);
            if (tasklistRow !== null) {
                //add item to this tasklist with id x_y
                $("#" + selectedTasklist + "_list").append(createTask(tasklistRow, item_string));
                let taskIndex = tasklistRow.tasklist.tasks.length - 1;
                let check = $("#check_id_" + tasklistRow.tasklist.name + '_' + taskIndex);
                let cross = $("#cross_id_" + tasklistRow.tasklist.name + '_' + taskIndex);
                addListListener(tasklistRow, check, cross, taskIndex);
                updateItemsLeft(tasklistRow.tasklist.tasks.length);
            }
            else {
                console.log("Creating item error");
            }
        }
        else {
            setAlert("Create a tasklist first");
        }
    }
});

clear_completed.addEventListener("click", function () {
    clearCompleted();
});

theme.addEventListener('click', function () {
    switchTheme();
});

all.addEventListener("click", function () {
    all.classList.add("active");
    active.classList.remove("active");
    completed.classList.remove("active");
    showAll();
});

active.addEventListener("click", function () {
    all.classList.remove("active");
    active.classList.add("active");
    completed.classList.remove("active");
    showActive();
});

completed.addEventListener("click", function () {
    all.classList.remove("active");
    active.classList.remove("active");
    completed.classList.add("active");
    showCompleted()
});

tablinks_add.on('click', function (e) {
    if ($("#add_tasklist").hasClass("display-block")) {
        $("#add_tasklist").removeClass("display-block");
        $("#add_tasklist").addClass("display-none");
    }
    else {
        $("#add_tasklist").removeClass("display-none");
        $("#add_tasklist").addClass("display-block");
    }
});

add_tasklist.on('keypress', function (e) {
    if (e.key === 'Enter' && add_tasklist.val() != "") {
        let item_string = add_tasklist.val();
        add_tasklist.val("");

        //if tasklists array already exist
        if (tasklists.length !== 0) {
            //get last tasklist name btn to append to
            let lastTasklistName = tasklists[tasklists.length - 1].name;
            htmlElements = createTasklist(item_string);
            $("#" + lastTasklistName + "_btn").after(htmlElements.button);
            $("#" + lastTasklistName).after(htmlElements.div);
        }
        else {
            //no tasklists array, create one and append elements differently
            htmlElements = createTasklist(item_string);
            tablinks_add.before(htmlElements.button);
            $("#tabs").after(htmlElements.div);
        }
        $("#" + item_string + "_btn").on('click', function (e) {
            openTasklist(e, item_string);
        });
        $("#" + item_string + "_btn").click();
    }
});

alert.on('click', function (e) {
    $("#alert_text").html("");
    alert.addClass("invisible");
});


/* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("sidenav").style.width = "250px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("sidenav").style.width = "0";
}

