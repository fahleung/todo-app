//create item
function createItem(tasklistRow, text) {
    console.log(tasklistRow);
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
    p.innerHTML = text.charAt(0).toUpperCase() + text.slice(1);

    span.appendChild(check_img);
    span.appendChild(p);
    //span.innerHTML = span.innerHTML + text.charAt(0).toUpperCase() + text.slice(1);
    li.appendChild(span);
    li.appendChild(cross_img);

    //add task to tasklist
    let task = {
        name: 'task25',
        time: null,
        completed: false
    };
    tasklists[tasklistRow.index].tasks.push(task);

    //TODO SAVE TO DB

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

function addListListener(check, cross, index) {
    /*let index = tasklist.tasks.length - 1;
    let check = $("#check_id_" + tasklist.name + '_' + index);
    let cross = $("#cross_id_" + tasklist.name + '_' + index);*/

    //check hover
    check.on("mouseover", function () {
        //console.log("over");
        if (!check.hasClass('check') && !check.hasClass('border-colored')) {
            check.removeClass('border-white');
            check.addClass('border-colored');
        }
    });

    check.on("mouseout", function () {
        //console.log("out");
        if (!check.hasClass('check') && check.hasClass('border-colored')) {
            check.removeClass('border-colored');
            check.addClass('border-white');
        }
    });

    check.on("click", function () {
        //console.log("click");
        if (!check.hasClass('check')) {
            check.removeClass('border-colored');
            check.removeClass('border-white');
            check.addClass('check');
            check.next().addClass('line');
            item_number--;
        }
        else {
            check.removeClass('border-colored');
            check.addClass('border-white');
            check.removeClass('check');
            check.next().removeClass('line');
            item_number++;
        }
        updateItemsLeft(item_number);
    });

    //item
    cross.on("click", function () {
        //console.log("cross click");
        if (!check.hasClass('check')) {
            item_number--;
            updateItemsLeft(item_number);
        }
        cross.parent().remove();

    });
}

//clear items checked
function clearCompleted(itemCounter) {
    console.log(itemCounter);
    for (let i = 1; i <= itemCounter; i++) {
        let check = document.querySelector("#check_id_" + i);
        let cross = document.querySelector("#cross_id_" + i);
        if (check != null && check.classList.contains('check')) {
            cross.parentElement.remove();
        }
    }
}

//function update items left number
function updateItemsLeft(number) {
    item_left.innerHTML = item_number + " items left";
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
    console.log(itemCounter);
    for (let i = 1; i <= itemCounter; i++) {
        let check = document.querySelector("#check_id_" + i);
        if (check != null && !check.classList.contains('check')) {
            check.parentElement.parentElement.classList.add('invisible');
        }
    }
}

function showActive() {
    showAll();
    console.log(itemCounter);
    for (let i = 1; i <= itemCounter; i++) {
        let check = document.querySelector("#check_id_" + i);
        if (check != null && check.classList.contains('check')) {
            check.parentElement.parentElement.classList.add('invisible');
        }
    }
}

function showAll() {
    console.log(itemCounter);
    for (let i = 1; i <= itemCounter; i++) {
        let check = document.querySelector("#check_id_" + i);
        if (check != null) {
            check.parentElement.parentElement.classList.remove('invisible');
        }
    }
}

function openTasklist(evt, tasklistName) {
    var i, tabcontent, tablinks;
    selectedTasklist = tasklistName;

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
