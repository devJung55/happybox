myPageDistributorService.productListAjax();

function showProductList(products) {
    const $productUl = $(".product__list");
    let text = "";

    products.content.forEach(product => {
        let img = "";
       text += `
            <li class="ext-li colum">
                <input type="hidden" class="productId" value="${product.id}">
                <div class="prd-item type-sm2">
                    <figure class="img w180">
                        <a href="/product/detail/${product.id}">
            `;

            for (let i = 0; i < 1; i++) {
                if(product.productFileDTOS.length != 0) {
                    img  = `<img class="lozad" src="/image/display?fileName=${product.productFileDTOS[i].filePath}/${product.productFileDTOS[i].fileUuid}_${product.productFileDTOS[i].fileOrgName}">`
                } else {
                    img  = `<img class="lozad" src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">`
                }
            }

       text += img;

       text += `
                        </a>
                    </figure>
                    <div class="desc-bottom">
                        <div class="brand-name">${product.productName}</div>
                        <p class="origin"><span>${product.productPrice}</span>원</p>
                        <p class="origin"><span>${product.productStock}</span>개</p>
                    </div>
                </div>
            </li>
       `;
    });
    $productUl.empty();
    $productUl.append(text);
}