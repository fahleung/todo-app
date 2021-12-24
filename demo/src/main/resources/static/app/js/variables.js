const add_todo = document.querySelector("#add_todo");
const clear_completed = document.querySelector('#clear_completed');
const item_left = document.querySelector('#item_left');

var isLightTheme = true;

//alert
const alert = $("#alert");

//tabs
const tabs = document.querySelector("#tabs");
const tablinks_add = $("#tablinks_add");

//sidenav
const sidenav = document.querySelector("#sidenav");

//theme
const theme = document.querySelector('#theme');
const moon_logo = "../../images/icon-moon.svg";
const sun_logo = "../../images/icon-sun.svg";
const cross_img_url = "../../images/icon-cross.svg";
const check_img_url = "../../images/icon-check.svg";

//filters
const all = document.querySelector('#all');
const active = document.querySelector('#active');
const completed = document.querySelector('#completed');

//tasklists
var selectedTasklist;
const add_tasklist = $("#add_tasklist");