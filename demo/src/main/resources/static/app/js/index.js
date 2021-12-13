var goToRegisterLink = document.querySelector("#goToRegister");
var registerForm = document.querySelector("#register_form");
var loginForm = document.querySelector("#login_form");
var goBackArrow = document.querySelector("#goBackArrow");
const indexFormTitle = document.querySelector("#index__form_title");


goToRegisterLink.addEventListener("click", function()
{
    loginForm.classList.add("invisible");
    registerForm.classList.remove("invisible");
    indexFormTitle.innerHTML = "Register to continue !";
    goToRegisterLink.classList.add("invisible");
    goBackArrow.classList.remove("invisible");
})

goBackArrow.addEventListener("click", function()
{
    loginForm.classList.remove("invisible");
    registerForm.classList.add("invisible");
    indexFormTitle.innerHTML = "Login to continue !";
    goToRegisterLink.classList.remove("invisible");
    goBackArrow.classList.add("invisible");
})