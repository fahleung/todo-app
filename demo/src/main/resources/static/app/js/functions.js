//create item
function createItem(tasklistRow, taskName) {
    let li = document.createElement('li');
    let span = document.createElement('span');
    let check_img = document.createElement('img');
    let cross_img = document.createElement('img');
    let p = document.createElement('p');

    //li setup
    li.classList.add('flex');
    li.classList.add('flex-jc-sb');
    li.classList.add('flex-ai-c');
    li.draggable = true;
    //span setup
    span.classList.add('flex');
    span.classList.add('flex-ai-c');
    span.classList.add('gap-1-10');
    //check img setup
    check_img.id = "check_id_" + tasklistRow.tasklist.name + '_' + tasklistRow.tasklist.tasks.length;
    check_img.classList.add('border-white');
    check_img.src = check_img_url;
    //cross img setup
    cross_img.id = "cross_id_" + tasklistRow.tasklist.name + '_' + tasklistRow.tasklist.tasks.length;
    cross_img.classList.add('cross');
    cross_img.src = cross_img_url;
    //p
    p.innerHTML = taskName.charAt(0).toUpperCase() + taskName.slice(1);

    span.appendChild(check_img);
    span.appendChild(p);

    li.appendChild(span);
    li.appendChild(cross_img);

    //save
    tasklists = addTask(tasklists, tasklistRow.index, taskName);
    //li.addEventListener('ondragstart', onDragStart(event))
    return li;
}

function getTasklistByName(name, tasklists) {
    if (tasklists) {
        let i = 0;
        let tasklistFound = false;
        let tasklistToReturn;
        while (!tasklistFound && i <= tasklists.length) {
            if (tasklists[i].name == name) {
                tasklistFound = true;
                tasklistToReturn = {
                    tasklist: tasklists[i],
                    index: i
                }
            }
            else {
                i++;
            }
        }
        return tasklistToReturn;
    }
}

function addListListener(tasklistRow, check, cross, taskIndex) {

    //check hover
    check.on("mouseover", function () {
        if (!check.hasClass('check') && !check.hasClass('border-colored')) {
            check.removeClass('border-white');
            check.addClass('border-colored');
        }
    });

    check.on("mouseout", function () {
        if (!check.hasClass('check') && check.hasClass('border-colored')) {
            check.removeClass('border-colored');
            check.addClass('border-white');
        }
    });

    check.on("click", function () {
        let isCompleted;
        if (!check.hasClass('check')) {
            check.removeClass('border-colored');
            check.removeClass('border-white');
            check.addClass('check');
            check.next().addClass('line');
            isCompleted = true;
        }
        else {
            check.removeClass('border-colored');
            check.addClass('border-white');
            check.removeClass('check');
            check.next().removeClass('line');
            isCompleted = false;
        }
        //complete
        tasklists = completeTask(tasklists, tasklistRow.index, taskIndex, isCompleted);
        updateItemsLeft(tasklistRow.tasklist.tasks.length);
    });

    //item
    cross.on("click", function () {
        if (!check.hasClass('check')) {
            //delete
            tasklists = deleteTask(tasklists, tasklistRow.index, taskIndex);
            updateItemsLeft(tasklistRow.tasklist.tasks.length);
        }
        cross.parent().remove();
    });
}

//clear items checked
function clearCompleted() {
    let tasklistRow = getTasklistByName(selectedTasklist, tasklists);
    tasklistRow.tasklist.tasks.forEach(function (e, i) {
        let check = $("#check_id_" + tasklistRow.tasklist.name + '_' + i);
        let cross = $("#cross_id_" + tasklistRow.tasklist.name + '_' + i);
        if (check != null && check.hasClass('check')) {
            cross.parent().remove();
        }
        tasklists = deleteTask(tasklists, tasklistRow.index, i);
        updateItemsLeft(tasklistRow.tasklist.tasks.length);
    });
}

//function update items left number
function updateItemsLeft(number) {
    item_left.innerHTML = number + " items";
}

//change light theme <=> dark theme
function switchTheme() {
    console.log("switch theme");
    isLightTheme = !isLightTheme;
    let body = document.querySelector('body');
    let header = document.querySelector('.header');

    if (isLightTheme) {
        body.classList.remove('light');
        body.classList.add('dark');
        header.classList.remove('light');
        header.classList.add('dark');
        input.classList.remove('light');
        input.classList.add('dark');
        sidenav.classList.remove("light");
        sidenav.classList.add("dark");
        tabs.classList.remove("light");
        tabs.classList.add("dark");
        theme.src = sun_logo;
    }
    else {
        body.classList.remove('dark');
        body.classList.add('light');
        header.classList.remove('dark');
        header.classList.add('light');
        input.classList.remove('dark');
        input.classList.add('light');
        sidenav.classList.remove('dark');
        sidenav.classList.add('light');
        tabs.classList.remove('dark');
        tabs.classList.add('light');
        theme.src = moon_logo;
    }
}

//drag and drop (exchange places between different elements)
function onDragStart(event) {
    console.log(event);
    event.dataTransfer.setData('text', event.target.id);
    event.currentTarget.style.backgroundColor = 'yellow';
}

//filters
function showCompleted() {
    showAll();
    let tasklistRow = getTasklistByName(selectedTasklist, tasklists);
    tasklistRow.tasklist.tasks.forEach(function (e, i) {
        let check = $("#check_id_" + tasklistRow.tasklist.name + '_' + i);
        if (check != null && !check.hasClass('check')) {
            check.parent().parent().addClass('invisible');
        }
    });
}

function showActive() {
    showAll();
    let tasklistRow = getTasklistByName(selectedTasklist, tasklists);
    tasklistRow.tasklist.tasks.forEach(function (e, i) {
        let check = $("#check_id_" + tasklistRow.tasklist.name + '_' + i);
        if (check != null && check.hasClass('check')) {
            check.parent().parent().addClass('invisible');
        }
    });
}

function showAll() {
    let tasklistRow = getTasklistByName(selectedTasklist, tasklists);
    tasklistRow.tasklist.tasks.forEach(function (e, i) {
        let check = $("#check_id_" + tasklistRow.tasklist.name + '_' + i);
        if (check != null) {
            check.parent().parent().removeClass('invisible');
        }
    });
}

function openTasklist(evt, tasklistName) {
    var i, tabcontent, tablinks;
    selectedTasklist = tasklistName;
    updateItemsLeft(getTasklistByName(selectedTasklist, tasklists).tasklist.tasks.length);

    tabcontent = document.getElementsByClassName("tabs__content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    document.getElementById(tasklistName).style.display = "block";
    evt.target.className += " active";

}
