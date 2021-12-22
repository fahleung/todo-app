const list = document.querySelector("#list");
const input = document.querySelector("#add_todo");
const cross_img_url = "../../images/icon-cross.svg";
const check_img_url = "../../images/icon-check.svg";
const clear_completed = document.querySelector('#clear_completed');
const item_left = document.querySelector('#item_left');
var item_number = 0;
const theme = document.querySelector('#theme');
const sidenav = document.querySelector("#sidenav");
const tabs = document.querySelector("#tabs");
var isLightTheme = true;

const moon_logo = "../../images/icon-moon.svg";
const sun_logo = "../../images/icon-sun.svg";

const all = document.querySelector('#all');
const active = document.querySelector('#active');
const completed = document.querySelector('#completed');

//tasklists
var selectedTasklist;