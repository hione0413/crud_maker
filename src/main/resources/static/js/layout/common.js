var CONTEXT_PATH = getContextPath();
var CSRF_TOKEN = $('[name=_csrf]').attr('content');
var fileUploadApiRequestUrl = "/file";

$(document).ready(function () {
    //윈도우 사이즈에 맞춰 card height 크기 조절
    contentHeight();
    window.onresize = contentHeight;

    // Memo : file 업로드 버튼은 공통 코드가 아님 -> 이벤트 핸들러는 사용하는 페이지로 옮길 것
    // // 단일 업로드 버튼 클릭 이벤트(validation 포함)
    // $('#singleFileUploadBtn').on('click', function () {
    //     var singleFileUploadInputId = 'singleFileUploadInput';
    //     uploadSingleFile(singleFileUploadInputId);
    // });
    // // 다중 업로드 버튼 클릭 이벤트(validation 포함)
    // $('#multipleFileUploadBtn').on('click', function () {
    //     var multipleFileUploadInputId = 'multipleFileUploadInput';
    //     uploadMultipleFiles(multipleFileUploadInputId);
    // });
});


function getContextPath() {
    return $("meta[name='_ctx']").attr("content") ? $("meta[name='_ctx']").attr("content") : "";
}


/** 메뉴 선택에 의한 iframe src 변경 : 구조 변경으로 인한 임시 주석처리 */
// function changeUrl(sendUrl) {
//     getPageHtml('/cli/iframe');
//     $.ajax({
//         url: CONTEXT_PATH + '/superset/pageurl',
//         type: 'GET',
//         data: {
//             supersetPageName: sendUrl,
//         },
//         success: function (msg) {
//             ;
//             var targetUrl = msg.supersetUrl;
//             $('#targetFrm').attr('src', targetUrl);
//         },
//         error: function (xhr, status, error) {
//             console.log(error);
//             getErrorMsg(xhr.status);
//         }
//     });
// }


/** Content Header Setting */
function setContentHeaderByMenuName(menuName) {
    $("#contentHeaderTitle").html(menuName);
}


/** 메뉴이동 : 함수 호출 html 은 서버에서 생성하여 준다. */
function getPageHtml(menuId) {
    // $("li.nav-item.active").removeClass("active");
    // $("#menu_id_" + menuId).addClass("active");

    $.ajax({
        url: CONTEXT_PATH + '/gotopage/' +  menuId,
        type: 'GET',
        async: false,
        dataType: 'html',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("X-CSRF-TOKEN", CSRF_TOKEN);
        },
        success: function (msg) {
            $('#initContent').html(msg);
            contentHeight();
        },
        error: function (xhr, status, error) {
            console.log(error);
            getErrorMsg(xhr.status);
        }
    });
}


/** 브라우저 창 size 가 변경될 때 마다 card형 content 들의 높이를 유동적으로 변경  */
function contentHeight() {
    var windowHeight = $(window).height() ? $(window).height() : 0;
    var footerHeight = $(".main-footer").innerHeight() ? $(".main-footer").innerHeight() : 0;
    var headerHeight = $(".main-header ").innerHeight() ? $(".main-header ").innerHeight() : 0;

    var contentHeight = windowHeight - footerHeight - headerHeight;
    var tbPadding = "18";
    var contentHeightUnit = "px;";
    var cardBodyHeight = $('#card-body').height();
    var cardFooterHeight = $('#card-footer').height();

    $('.admin-content').attr("style", "height:" + (contentHeight - tbPadding - 128) + contentHeightUnit);
    $('.user-content').attr("style", "height:" + (contentHeight - tbPadding) + contentHeightUnit);
    $('.hive-ug-content').attr("style", "height:" + (contentHeight - tbPadding - cardBodyHeight - cardFooterHeight - 64) + contentHeightUnit);

}

/** ajax실행 결과 error발생 시 SweetAlert으로 나타내기 */
function getErrorMsg(jqXHRStatus) {

    if (jqXHRStatus === 400) {
        Swal.fire({
            title: '400 : Bad request',
            html: '잘못된 요청입니다. 관리자에게 문의해주세요.',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        }).then(function (args) {
            if (args.isConfirmed) {

            }
        });
    }
    if (jqXHRStatus === 403) {
        Swal.fire({
            title: '403 : Forbidden',
            html: '접근 권한이 없습니다. 로그인 정보를 확인해주세요.',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        }).then(function (args) {
            if (args.isConfirmed) {

            }
        });
    }
    if (jqXHRStatus === 404) {
        Swal.fire({
            title: '404 : Not found',
            html: '페이지를 찾을 수 없습니다',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        });
    }
    if (jqXHRStatus === 405) {
        Swal.fire({
            title: '405 : Method not allowed',
            html: '리소스를 허용할 수 없습니다',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        });
    }

    //DB관련 Test Connection용(hiveGroup.js)
    if (jqXHRStatus === 422) {
        Swal.fire({
            title: '422 : Unprocessable Entity',
            html: '데이터 커넥션 정보가 유효하지 않습니다',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        });
    }

    if (jqXHRStatus === 500) {
        Swal.fire({
            title: '500 : Internal server error',
            html: '내부서버 오류입니다',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        });
    }
}


/** 파일 업로드 & 다운로드 관련 소스 Start - yjcho **************************************/

/** 단일 파일 업로드
 * singleFileUploadInputId : input Id (type - file)
 * uploadSuccessCallback : function - 파일 업로드 후 성공 시 callback Function
 * */
function uploadSingleFile(singleFileUploadInputId, uploadSuccessCallback) {
    var singleFileUploadInput = document.querySelector('#' + singleFileUploadInputId);
    var file = singleFileUploadInput.files;

    if (file.length === 0) {
        // TODO : swal
        Swal.fire({
            title: '파일 미선택',
            html: '업로드 할 파일을 선택해 주세요',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        });
        return;
    }

    var formData = new FormData();
    formData.append("file", file[0]);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", CONTEXT_PATH + fileUploadApiRequestUrl + '/uploadFile');

    xhr.onload = function () {
        var response = JSON.parse(xhr.responseText);

        if (xhr.status == 200 || xhr.status == 201) {
            // upload 성공

            // singleFileUploadError.style.display = "none";
            // singleFileUploadSuccess.innerHTML = "<p>파일이 성공적으로 업로드 되었습니다.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
            // singleFileUploadSuccess.style.display = "block";

            //※대리님께 상의
            /*if(uploadSuccessCallback) {
                // TODO : 고민
                uploadSuccessCallback(response);
            } else {
                // TODO : swal
                Swal.fire({
			        title: '파일업로드 실패',
			        html: '에러가 발생했습니다',
			        icon: 'warning',
			        confirmButtonText: '확인',
			        showCancelButton: false,
		        });
		        return;
            }*/
            Swal.fire({
                title: '파일업로드 성공',
                html: "<p>파일이 성공적으로 업로드 되었습니다.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>",
                icon: 'success',
                confirmButtonText: '확인',
                showCancelButton: false,
            });
            $('#singleFileUploadInput').val("");
            //uploadSuccessCallback(response);

        } else {
            // upload 실패
            // TODO : swal
            Swal.fire({
                title: '파일업로드 실패',
                html: '에러가 발생했습니다',
                icon: 'warning',
                confirmButtonText: '확인',
                showCancelButton: false,
            });
            return;
        }
    }

    xhr.setRequestHeader("X-CSRF-TOKEN", CSRF_TOKEN);
    xhr.send(formData);
}


/** 멀티 파일 업로드
 * multipleFileUploadInputId : input Id (type - file)
 * uploadSuccessCallback : function - 파일 업로드 후 성공 시 callback Function
 * */
function uploadMultipleFiles(multipleFileUploadInputId, uploadSuccessCallback) {
    var multipleFileUploadInput = document.querySelector('#' + multipleFileUploadInputId);
    var files = multipleFileUploadInput.files;

    if (files.length === 0) {
        // TODO : swal
        Swal.fire({
            title: '파일 미선택',
            html: '한 개 이상의 파일을 선택해 주세요',
            icon: 'warning',
            confirmButtonText: '확인',
            showCancelButton: false,
        });
        return;
    }

    var formData = new FormData();
    for (var index = 0; index < files.length; index++) {
        formData.append("files", files[index]);
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", CONTEXT_PATH + fileUploadApiRequestUrl + '/uploadMultipleFiles');

    xhr.onload = function () {
        var response = JSON.parse(xhr.responseText);

        if (xhr.status == 200 || xhr.status == 201) {
            // upload 성공

            // multipleFileUploadError.style.display = "none";
            // var content = "<p>모든 파일이 성공적으로 업로드 되었습니다.</p>";
            // for(var i = 0; i < response.length; i++) {
            //     content += "<p>DownloadUrl : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
            // }
            // multipleFileUploadSuccess.innerHTML = content;
            // multipleFileUploadSuccess.style.display = "block";


            //※대리님께 상의
            /*if(uploadSuccessCallback) {
                // TODO : 고민
                uploadSuccessCallback(response);
            } else {
                // TODO : swal
                Swal.fire({
			        title: '파일업로드 실패',
			        html: '에러가 발생했습니다',
			        icon: 'warning',
			        confirmButtonText: '확인',
			        showCancelButton: false,
		         });
		         return;
            }*/

            var content = "";
            for (var i = 0; i < response.length; i++) {
                content += "<p>DownloadUrl : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
            }

            Swal.fire({
                title: '파일업로드 성공',
                html: content,
                icon: 'success',
                confirmButtonText: '확인',
                showCancelButton: false,
            });
            $('#multipleFileUploadInput').val("");
            //uploadSuccessCallback(response);

        } else {
            // upload 실패
            // TODO : swal
            Swal.fire({
                title: '파일업로드 실패',
                html: '에러가 발생했습니다',
                icon: 'warning',
                confirmButtonText: '확인',
                showCancelButton: false,
            });
            return;
        }
    }
    xhr.setRequestHeader("X-CSRF-TOKEN", CSRF_TOKEN);
    xhr.send(formData);
}

/** 파일 업로드 & 다운로드 관련 소스 End - yjcho **************************************/
