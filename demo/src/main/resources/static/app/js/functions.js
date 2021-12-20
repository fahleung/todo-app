//create item
function createItem(id, text) {
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
    check_img.id = "check_id_" + id;
    check_img.classList.add('border-white');
    check_img.src = check_img_url;
    //cross img setup
    cross_img.id = "cross_id_" + id;
    cross_img.classList.add('cross');
    cross_img.src = cross_img_url;
    //p
    p.innerHTML = text.charAt(0).toUpperCase() + text.slice(1);

    span.appendChild(check_img);
    span.appendChild(p);
    //span.innerHTML = span.innerHTML + text.charAt(0).toUpperCase() + text.slice(1);
    li.appendChild(span);
    li.appendChild(cross_img);

    //li.addEventListener('ondragstart', onDragStart(event))
    return li;
}

function addListListener(id) {
    let check = document.querySelector("#check_id_" + id);
    let cross = document.querySelector("#cross_id_" + id);
    //check hover
    check.addEventListener("mouseover", function () {
        //console.log("over");
        if (!check.classList.contains('check') && !check.classList.contains('border-colored')) {
            check.classList.remove('border-white');
            check.classList.add('border-colored');
        }
    });

    check.addEventListener("mouseout", function () {
        //console.log("out");
        if (!check.classList.contains('check') && check.classList.contains('border-colored')) {
            check.classList.remove('border-colored');
            check.classList.add('border-white');
        }
    });

    check.addEventListener("click", function () {
        //console.log("click");
        if (!check.classList.contains('check')) {
            check.classList.remove('border-colored');
            check.classList.remove('border-white');
            check.classList.add('check');
            check.nextSibling.classList.add('line');
            item_number--;
        }
        else {
            check.classList.remove('border-colored');
            check.classList.add('border-white');
            check.classList.remove('check');
            check.nextSibling.classList.remove('line');
            item_number++;
        }
        updateItemsLeft(item_number);
    });

    //item
    cross.addEventListener("click", function () {
        //console.log("cross click");
        if (!check.classList.contains('check')) {
            item_number--;
            updateItemsLeft(item_number);
        }
        cross.parentElement.remove();

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
