$(document).ready(function () {
    //open first tab by default
    if (tasklists) {
        $("#" + tasklists[0].name + "_btn").click();
    }
})

var itemCounter = 1;
var typing;
//input
input.addEventListener("focusin", function () {
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
        input.placeholder = placeholdertoshow;
    }

    //input.placeholder = 'Currently typing...';

});

input.addEventListener("focusout", function () {
    clearInterval(typing);
    input.placeholder = 'Create a new todo...';
});

input.addEventListener('keypress', function (e) {
    //create new item
    if (e.key === 'Enter' && input.value != "") {
        console.log("new item " + input.value);
        let item_string = input.value;
        input.value = "";

        let tasklistRow = getTasklistByName(selectedTasklist, tasklists);
        if (tasklistRow) {
            //add item to this tasklist with id x_y
            itemCounter++;
            list.appendChild(createItem(tasklistRow, item_string));
            addListListener(tasklistRow);
            item_number++;
            updateItemsLeft(item_number);
        }
        else {
            console.log("Creating item error");
        }


    }
});

clear_completed.addEventListener("click", function () {
    clearCompleted(itemCounter);
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

/* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("sidenav").style.width = "250px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("sidenav").style.width = "0";
}

