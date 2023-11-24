var header = document.querySelector("header"),
    mainMenuList = document.querySelectorAll(".mainmenu > li"),
    subMenu = document.querySelectorAll(".submenu"),
    headerHeight = header.offsetHeight,
    subMenuHeight = 0;

for (var i = 0; i < mainMenuList.length; i++) {
    mainMenuList[i].addEventListener("mouseover", function () {
        subMenuHeight = this.querySelector("ul").offsetHeight;
        header.style.height = headerHeight + subMenuHeight + "px";
        /*마우스가 올라간 그 요소의 자식 요소(ul)의 높이를 변수명 submenuheight가 저장하고
        header의 높이를 headerheight + submenuheight로 변경한다.*/
    });
    mainMenuList[i].addEventListener("mouseout", function () {
        header.style.height = headerHeight + "px";
    });
}
