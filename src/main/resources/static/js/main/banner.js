HTMLCollection.prototype.forEach = Array.prototype.forEach;
const banner = document.querySelector("div.banner");
const imageDiv = document.querySelectorAll("div.banner div");
const lastImageDiv = document.createElement("div");
const firstImageDiv = document.createElement("div");
const next = document.querySelector("div.next");
const prev = document.querySelector("div.prev");
const bannerCount = document.querySelector("span.banner-count");
const container = document.querySelector("section.container");
let checkArrow = false;
let count = 1;

const autoSlideTimer = 3500;
const autoSlideActionTimer = "transform 0.8s ease-in-out";
const arrowLockTimer = 800;
let auto = setInterval(autoSlide, autoSlideTimer);

let width = '1920';


const imgURLs = [
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230331/IMG1680OmE238942115.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230411/IMG1681Ykf186708594.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230404/IMG1680LmT591459747.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230407/IMG1680RoB828036914.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230323/IMG1679ktZ531033089.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230411/IMG1681mbu203379662.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230406/IMG1680fdZ769659131.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230125/IMG1674bgz638273881.jpg",
]

HTMLCollection.prototype.forEach = Array.prototype.forEach;

// imageDiv.forEach((div, i) => div.style.backgroundImage = `url(/image/00${i + 1}.png)`)
imageDiv.forEach((div, i) => div.style.backgroundImage = `url(${imgURLs[i]})`)

banner.appendChild(lastImageDiv);
lastImageDiv.style.backgroundImage = `url(${imgURLs[0]})`;

banner.insertBefore(firstImageDiv, document.querySelector("div.banner div"));
firstImageDiv.style.backgroundImage = `url(${imgURLs[imgURLs.length - 1]})`

banner.style.transform = `translate(-${width}px)`;
updateBannerCount();

function autoSlide() {
    banner.style.transition = `${autoSlideActionTimer}`;
    banner.style.transform = `translate(${-width * ++count}px)`;
    if (count == 9) {
        count = 1;
        updateBannerCount();
        setTimeout(function () {
            banner.style.transition = "transform 0s";
            banner.style.transform = `translate(-${width}px)`;
        }, arrowLockTimer);
    }
    updateBannerCount();
}

prev.addEventListener("click", function () {
    if (checkArrow) {
        return;
    }
    checkArrow = true;
    clearInterval(auto);
    banner.style.transition = `${autoSlideActionTimer}`;
    banner.style.transform = `translate(${-width * --count}px)`;
    if (count == 0) {
        count = 8;
        updateBannerCount();
        setTimeout(function () {
            banner.style.transition = "transform 0s";
            banner.style.transform = `translate(${-width * (imageDiv.length)}px)`;
        }, arrowLockTimer);
    }
    updateBannerCount();
    auto = setInterval(autoSlide, autoSlideTimer);
    setTimeout(() => {
        checkArrow = false
    }, 300);
});

next.addEventListener("click", function () {
    if (checkArrow) {
        return;
    }
    checkArrow = true;
    clearInterval(auto);
    banner.style.transition = `${autoSlideActionTimer}`;
    banner.style.transform = `translate(${-width * ++count}px)`;
    if (count == 9) {
        count = 1;
        updateBannerCount();
        setTimeout(function () {
            banner.style.transition = "transform 0s";
            banner.style.transform = `translate(-${width}px)`;
        }, arrowLockTimer);
    }
    updateBannerCount();
    auto = setInterval(autoSlide, autoSlideTimer);
    setTimeout(() => {
        checkArrow = false
    }, arrowLockTimer);
});

function updateBannerCount() {
    bannerCount.innerHTML = `${count} / ${imageDiv.length}`;
}