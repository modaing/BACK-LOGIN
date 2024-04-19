const { useEffect, useState } = React;

function Notice() {

    const [editorLoaded, setEditorLoaded] = useState(false);

    const pageTitleStyle  = {
        marginBottom: '20px',
        marginTop: '20px'
    };

    const cardTitleStyle = {
        marginLeft: '30px'
    };

    const contentStyle = {
        marginLeft: '100px'
    };

    const insertButton = {
        backgroundColor: '#112D4E',
        color: 'white',
        borderRadius: '15px',
        padding: '10px 20px',
        cursor: 'pointer',
        marginRight: '15px'
    };

    const cancelButton = {
        backgroundColor: '#ffffff',
        color: 'black',
        border: '1px solid #D5D5D5',
        borderRadius: '15px',
        padding: '10px 20px',
        cursor: 'pointer',
        marginRight: '15px'
    };

    const buttonClass = {
        marginTop: '20px',
        marginBottom: '20px',
        marginRight: '100px',
        textAlign: 'right'

    }

    useEffect(() => {
        const { Editor } = toastui;
        const { colorSyntax } = Editor.plugin;


        const editor = new Editor({
            el: document.querySelector('#editor'),
            height: '600px',
            initialEditType: 'markdown',
            previewStyle: 'vertical',
            usageStatistics: false,
            plugins: [colorSyntax]

        });
        setEditorLoaded(true);
    }, []);


    return (

        <main id="main" className="main">
            <div className="pagetitle" style={pageTitleStyle}>
                <h1>공지사항 등록</h1>
                <nav>
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item"><a href="/">Home</a></li>
                        <li className="breadcrumb-item">기타</li>
                        <li className="breadcrumb-item active">공지사항</li>
                    </ol>
                </nav>
            </div>
            <div className="col-lg-11">
                <div className="card">
                    <h5 className="card-title" style={cardTitleStyle}>Notice</h5>
                    <div className="content" style={contentStyle}>
                        <form>
                            <div className="row mb-3">
                                <label htmlFor="inputText" className="col-sm-1 col-form-label">제목</label>
                                <div className="col-sm-10">
                                    <input type="text" className="form-control" id="inputText"
                                           placeholder="제목을 입력해주세요"/>
                                </div>
                            </div>
                            <label htmlFor="inputText" className="col-sm-2 col-form-label">본문</label>
                            <div className="row mb-7">
                                <div className="col-sm-1"></div>
                                {/* 간격을 맞추기 위한 빈 칼럼 */}
                                <div className="col-sm-10">
                                    <div id='editor'></div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div style={buttonClass}>
                        <button className="notice-cancel-button" style={cancelButton}>취소하기</button>
                        <button className="notice-insert-button" style={insertButton}>등록하기</button>
                    </div>
                </div>
            </div>
        </main>
    )
}