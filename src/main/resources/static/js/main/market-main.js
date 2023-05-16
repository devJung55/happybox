recentTop8.forEach((product) => appendList(product, $(".recentTop8")));
likeCountTop8.forEach((product) => appendList(product, $(".likeCountTop8")));

function appendList(product, $target) {
    let text;

    let filePaths = product.productFileDTOS;
    let file = filePaths.filter(file => file.fileRepresent == "REPRESENT")[0];
    let repFilePath = "/img/market/no_img_market.png";

    if (filePaths.length != 0) {
        repFilePath = "/image/display?fileName=" + file.filePath + '/t_' + file.fileUuid + '_' + file.fileOrgName
    }

    text = `
        <li
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px">
        <a href="javascript:void(0)">
            <div class="image-container">
                <img
                        src="${repFilePath}"
                />
            </div>
        </a>
        <div class="product-info">
            <span class="distributer-name">${product.distributorName}</span>
            <h3 class="product-name">
                ${product.productName}
            </h3>
            <div class="content-row">
                        <span class="sales-price">
                            ${product.productPrice}<span class="won">원</span>
                        </span>
            </div>
            <div class="review-count">
                후기<span class="review-number">${product.productReplyCount}</span>개
            </div>
        </div>
    </li>
    `

    $target.append(text);
}

const $recipeTopImg = $(".recipie-top-img-container");
const recipeTopFile = recipeTop5[0].boardFiles[0];
const recipeTopFilePath = recipeTop5[0].boardFiles.length == 0 ?
    "/img/market/no_img_market.png" : "/image/display?fileName=" + recipeTopFile.filePath + '/t_' + recipeTopFile.fileUuid + '_' + recipeTopFile.fileOrgName;

$recipeTopImg.find("img").attr("src", recipeTopFilePath);
$(".recipe-top-title-wrap h3").text(recipeTop5[0].recipeBoardTitle);
$(".recipie-top-content").text(recipeTop5[0].recipeBoardContent);

for (let i = 1; i < recipeTop5.length; i++) {
    let text;

    let recipeFile = recipeTop5[i].boardFiles[0];
    let recipeFilePath = recipeTop5[i].boardFiles.length == 0 ?
        "/img/market/no_img_market.png" : "/image/display?fileName=" + recipeFile.filePath + '/t_' + recipeFile.fileUuid + '_' + recipeFile.fileOrgName;

    text = `
        <li>
            <a href="javascript:void(0)">
                <div class="recipie-img-container">
                    <img src="${recipeFilePath}">
                </div>
                <div class="recipie-content-wrap">
                            <span class="recipe-title-wrap">
                                <p class="recipe-title">
                                    ${recipeTop5[i].recipeBoardTitle}
                                </p>
                            </span>
                    <p class="recipie-content">
                         ${recipeTop5[i].recipeBoardContent}
                    </p>
                </div>
                <button class="recipie-detail-btn">
                    레시피 구경하기
                </button>
            </a>
        </li>
    `

    $(".recipie-top-others").append(text);
}