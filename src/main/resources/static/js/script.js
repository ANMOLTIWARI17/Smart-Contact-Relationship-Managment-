console.log("Script loaded");



let currentTheme=getTheme();
 document.addEventListener('DOMContentLoaded', () =>{
  changeTheme();
 });
//initially 
//TODO:
function changeTheme()
{
    //set to web page
    changePageTheme(currentTheme,currentTheme);

    //set the listener to change theme button
    const change_Theme_Button=document.querySelector('#theme_change_button')
    change_Theme_Button.querySelector('span').textContent=currentTheme=='light'?'Dark':"Light";
    change_Theme_Button.addEventListener("click", (event) => {
        let oldTheme=currentTheme;
        console.log("change theme button clicked");
        if(currentTheme=="dark"){
            currentTheme="light";
        }
        else
        {
          currentTheme="dark";
        }
        changePageTheme(currentTheme,oldTheme);
    });
}

//Set Theme to Local storage
function setTheme(theme)
{   const oldTheme=currentTheme;
    localStorage.setItem("theme",theme);
}




//get theme from local storage
function getTheme()
{
    let theme=localStorage.getItem("theme");
    return theme ? theme:"light";
}
// change current page theme 
function changePageTheme(theme,oldTheme)
{
    setTheme(currentTheme);
    document.querySelector("html").classList.remove(oldTheme);
    // set the current theme 
    document.querySelector("html").classList.add(theme);
    // change the text of button
    document.querySelector('#theme_change_button').querySelector('span').textContent=theme=='light'?'Dark':"Light";
}