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

/* 모바일 검사 */
let mobileCheck = screen.width < 1024;

const mobileImgURLs = [
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230331/IMG1680SkB239057354.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230404/IMG1680GKo591533564.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230411/IMG1681wlr203418124.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230414/IMG1681Wod449540365.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230420/IMG1681kGa984195345.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230418/IMG1681tCu783338373.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230404/IMG1680GKo591533564.jpg",
    "https://file.rankingdak.com/image/RANK/BANNER/AR_IMG_1/20230309/IMG1678aDj321881655.jpg",
];

let imgURLs = [
    "../../static/img/main/banner/001.png",
    "../../static/img/main/banner/002.png",
    "../../static/img/main/banner/003.png",
    "../../static/img/main/banner/004.png",
    "../../static/img/main/banner/005.png",
    "../../static/img/main/banner/006.png",
    "../../static/img/main/banner/007.png",
    "../../static/img/main/banner/008.png",
];

/* 배너 수 */
let bannersLength = imgURLs.length;

/* PC환경이면 1920px, 모바일이면 화면 크기에 맞춰서 */
let width = mobileCheck ? screen.width : 1920;

/* 모바일이면 모바일 배너로 바꾸기 */
imgURLs = mobileCheck ? mobileImgURLs : imgURLs;

/* container와 배너를 감싸고 있는 div 크기 조절 */
container.style.width = width + "px";
banner.style.width = width * (bannersLength + 2) + "px";

// imageDiv.forEach((div, i) => div.style.backgroundImage = `url(/image/00${i + 1}.png)`)
imageDiv.forEach((div, i) => (div.style.backgroundImage = `url(${imgURLs[i]})`));

banner.appendChild(lastImageDiv);
lastImageDiv.style.backgroundImage = `url(${imgURLs[0]})`;

banner.insertBefore(firstImageDiv, document.querySelector("div.banner div"));
firstImageDiv.style.backgroundImage = `url(${imgURLs[imgURLs.length - 1]})`;

document.querySelectorAll("div.banner div").forEach((e) => {
    e.style.width = width + "px";
});

banner.style.transform = `translate(-${width}px)`;
updateBannerCount();

function autoSlide() {
    banner.style.transition = `${autoSlideActionTimer}`;
    banner.style.transform = `translate(${-width * ++count}px)`;
    if (count == bannersLength + 1) {
        count = 1;
        updateBannerCount();
        setTimeout(function () {
            banner.style.transition = "transform 0s";
            banner.style.transform = `translate(-${width}px)`;
        }, arrowLockTimer);
    }
    updateBannerCount();
}

if (!mobileCheck) {
    prev.addEventListener("click", function () {
        if (checkArrow) {
            return;
        }
        checkArrow = true;
        clearInterval(auto);
        banner.style.transition = `${autoSlideActionTimer}`;
        banner.style.transform = `translate(${-width * --count}px)`;
        if (count == 0) {
            count = bannersLength;
            updateBannerCount();
            setTimeout(function () {
                banner.style.transition = "transform 0s";
                banner.style.transform = `translate(${-width * imageDiv.length}px)`;
            }, arrowLockTimer);
        }
        updateBannerCount();
        auto = setInterval(autoSlide, autoSlideTimer);
        setTimeout(() => {
            checkArrow = false;
        }, 300);
    });
}

if (!mobileCheck) {
    next.addEventListener("click", function () {
        if (checkArrow) {
            return;
        }
        checkArrow = true;
        clearInterval(auto);
        banner.style.transition = `${autoSlideActionTimer}`;
        banner.style.transform = `translate(${-width * ++count}px)`;
        if (count == bannersLength + 1) {
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
            checkArrow = false;
        }, arrowLockTimer);
    });
}

function updateBannerCount() {
    bannerCount.innerHTML = `${count} / ${imageDiv.length}`;
}