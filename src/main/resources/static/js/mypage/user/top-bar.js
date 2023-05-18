let filePath = $userFile.filePath;
let fileUuid = $userFile.fileUuid;
let fileOrgName = $userFile.fileOrgName;

console.log("드를어란어ㅣㅏㅇ리나어리")
console.log($userFile)
console.log(filePath);
console.log(fileUuid);
console.log(fileOrgName);

$(".imgPath").attr("src", `/image/display?fileName=${filePath}/${fileUuid}_${fileOrgName}`);