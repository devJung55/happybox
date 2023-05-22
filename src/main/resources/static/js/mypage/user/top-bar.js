/* 프로필 설정 */

console.log("ㄷㄹ어옴")
console.log($("#profile__label"));

$("#profile__label").on("change", function() {
    // 파일 찾아오기
    const $files = $(this)[i].files;

    console.log($files)
});