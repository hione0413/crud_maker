$(document).ready(function() {
    /** CRUD 소스 파일 생성 버튼 클릭 */
    $("#btn_make_crud").click(function(e) {
        handleClickMakeCrudBtn();
    });

    // 드랍다운 메뉴 item 선택 시 부모 메뉴 active 클래스 부여
    $(".dropdown-item").click(function(e) {
        $(e.target).closest("li").addClass("active");
    });
});


function handleClickMakeCrudBtn() {
    _makeCrudJavaSpring();
}

function _makeCrudJavaSpring() {
    $.ajax({
        url : getContextPath() + "/makecrud/java/spring/create",
        type : 'POST',
        contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
        timeout: 10000,
        xhrFields: {
            responseType: "blob",
        },
        beforeSend:function(xhr){
            xhr.setRequestHeader("X-CSRF-TOKEN", CSRF_TOKEN);
        },
        success : function(data, status, xhr) {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let disposition = xhr.getResponseHeader('Content-Disposition');
                let filename;

                if (disposition && disposition.indexOf('attachment' !== -1)) {
                    let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;

                    let matches = filenameRegex.exec(disposition);
                    if(matches != null && matches[1]) {
                        filename = matches[1].replace(/['"]/g, '');
                    }
                }

                let blob = new Blob([data]);
                let link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = filename;
                link.click();
            } else {
                alert("다운로드에 실패하였습니다.");
            }

        },
        error : function(request,status,error){
            console.log("[makeCrudJavaSpring] error request", request);
            console.log("[makeCrudJavaSpring] error status", status);
            console.log("[makeCrudJavaSpring] error error", error);
        },
        fail : function() {
            console.log("[makeCrudJavaSpring] fail");
        },
        complete:function(){
            console.log("[makeCrudJavaSpring] complete");
        }
    });
}