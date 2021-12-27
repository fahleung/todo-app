$(document).ready(function () {
    console.log(tasklists);

    //open first tab by default
    if (tasklists.length !== 0) {
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
                    check.removeClass('border-colored');
                    check.removeClass('border-white');
                    check.addClass('check');
                    check.next().addClass('line');
                    //check.click();
                }
            });
        });
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
        //save task
        if (selectedTasklist != null && tasklists.length !== 0) {
            addTask(selectedTasklist, item_string);
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
        addTasklist(item_string);
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

