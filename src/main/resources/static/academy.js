let curPos= 0;
let position = 0;
const IMAGE_WIDTH = 600;

const prevBtn = document.querySelector(".prev")
const nextBtn = document.querySelector(".next")
const images = document.querySelector(".images")


function prev(){
    if(curPos > 0){
    nextBtn.removeAttribute("disabled")
    position += IMAGE_WIDTH

    images.style.transform = `translateX(${position}px)`
    curPos -= 1;
    }
    if(curPos == 0){
        prevBtn.setAttribute("disabled", 'true')
    }
 }
function next(){
    if(curPos < 3){
        prevBtn.removeAttribute("disabled")
        position -= IMAGE_WIDTH

        images.style.transform = `translateX(${position}px)`
        curPos += 1;
    }
    if(curPos == 3){
        nextBtn.setAttribute("disabled", 'true')
    }
}

function init(){

    prevBtn.setAttribute("disabled", 'true')
    prevBtn.addEventListener("click", prev)
    nextBtn.addEventListener("click", next)
}

init();

// 강사 리스트

let TcurPos= 0;
let Tposition = 0;
const TIMAGE_WIDTH = 500;

const TprevBtn = document.querySelector(".t-prev")
const TnextBtn = document.querySelector(".t-next")
const Timages = document.querySelector(".t-images")


function Tprev(){
    if(TcurPos > 0){
    TnextBtn.removeAttribute("disabled")
    Tposition += TIMAGE_WIDTH

    Timages.style.transform = `translateX(${Tposition}px)`
    TcurPos -= 1;
    }
    if(TcurPos == 0){
        TprevBtn.setAttribute("disabled", 'true')
    }
 }
function Tnext(){
    if(TcurPos < 2){
        TprevBtn.removeAttribute("disabled")
        Tposition -= TIMAGE_WIDTH

        Timages.style.transform = `translateX(${Tposition}px)`
        TcurPos += 1;
    }
    if(TcurPos == 2){
        TnextBtn.setAttribute("disabled", 'true')
    }
}

function Tinit(){

    TprevBtn.setAttribute("disabled", 'true')
    TprevBtn.addEventListener("click", Tprev)
    TnextBtn.addEventListener("click", Tnext)
}

Tinit();