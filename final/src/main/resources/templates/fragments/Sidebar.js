function Sidebar() {
    return (
        <aside id="sidebar" className="sidebar">
            <ul className="sidebar-nav" id="sidebar-nav">
                <li className="nav-item">
                    <a className="nav-link" href="../index.html">
                        <i className="bi bi-grid"></i>
                        <span>Main</span>
                    </a>
                </li>
                <li className="nav-item">
                    <a className="nav-link collapsed" data-bs-target="#components-nav" data-bs-toggle="collapse"
                       href="#">
                        <i className="bi bi-calendar-check"></i><span>캘린더</span><i
                        className="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="components-nav" className="nav-content collapse" data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="components-alerts.html">
                                <i className="bi bi-circle"></i><span>Alerts</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-alerts.html">
                                <i className="bi bi-circle"></i><span>Alerts</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-accordion.html">
                                <i className="bi bi-circle"></i><span>Accordion</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-badges.html">
                                <i className="bi bi-circle"></i><span>Badges</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-breadcrumbs.html">
                                <i className="bi bi-circle"></i><span>Breadcrumbs</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-buttons.html">
                                <i className="bi bi-circle"></i><span>Buttons</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-cards.html">
                                <i className="bi bi-circle"></i><span>Cards</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-carousel.html">
                                <i className="bi bi-circle"></i><span>Carousel</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-list-group.html">
                                <i className="bi bi-circle"></i><span>List group</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-modal.html">
                                <i className="bi bi-circle"></i><span>Modal</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-tabs.html">
                                <i className="bi bi-circle"></i><span>Tabs</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-pagination.html">
                                <i className="bi bi-circle"></i><span>Pagination</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-progress.html">
                                <i className="bi bi-circle"></i><span>Progress</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-spinners.html">
                                <i className="bi bi-circle"></i><span>Spinners</span>
                            </a>
                        </li>
                        <li>
                            <a href="components-tooltips.html">
                                <i className="bi bi-circle"></i><span>Tooltips</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li className="nav-item">
                    <a className="nav-link collapsed" data-bs-target="#forms-nav" data-bs-toggle="collapse"
                       href="#">
                        <i className="bi bi-clock"></i><span>출퇴근</span><i
                        className="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="forms-nav" className="nav-content collapse" data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="forms-elements.html">
                                <i className="bi bi-circle"></i><span>출퇴근 관리</span>
                            </a>
                        </li>
                        <li>
                            <a href="forms-layouts.html">
                                <i className="bi bi-circle"></i><span>월 별 근태 관리</span>
                            </a>
                        </li>
                        <li>
                            <a href="forms-editors.html">
                                <i className="bi bi-circle"></i><span>일 별 근태 관리</span>
                            </a>
                        </li>
                        <li>
                            <a href="forms-validation.html">
                                <i className="bi bi-circle"></i><span>근태 통계</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li className="nav-item">
                    <a className="nav-link collapsed" data-bs-target="#tables-nav" data-bs-toggle="collapse"
                       href="#">
                        <i className="bi bi-sunglasses"></i><span>휴가</span><i
                        className="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="tables-nav" className="nav-content collapse" data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="tables-general.html">
                                <i className="bi bi-circle"></i><span>휴가 신청 내역 조회</span>
                            </a>
                        </li>
                        <li>
                            <a href="tables-data.html">
                                <i className="bi bi-circle"></i><span>휴가 등록</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li className="nav-item">
                    <a className="nav-link collapsed" data-bs-target="#charts-nav" data-bs-toggle="collapse"
                       href="#">
                        <i className="bi bi-journal-check"></i><span>전자결재</span><i
                        className="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="charts-nav" className="nav-content collapse" data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="charts-chartjs.html">
                                <i className="bi bi-circle"></i><span>결재 작성하기</span>
                            </a>
                        </li>
                        <li>
                            <a href="charts-apexcharts.html">
                                <i className="bi bi-circle"></i><span>결재 상신함</span>
                            </a>
                        </li>
                        <li>
                            <a href="charts-echarts.html">
                                <i className="bi bi-circle"></i><span>결재 수신함</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li className="nav-item">
                    <a className="nav-link collapsed" data-bs-target="#icons-nav" data-bs-toggle="collapse"
                       href="#">
                        <i className="ri-organization-chart"></i><span>조직</span><i
                        className="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="icons-nav" className="nav-content collapse" data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="icons-bootstrap.html">
                                <i className="bi bi-circle"></i><span>인사이트</span>
                            </a>
                        </li>
                        <li>
                            <a href="icons-remix.html">
                                <i className="bi bi-circle"></i><span>공지사항</span>
                            </a>
                        </li>
                        <li>
                            <a href="icons-boxicons.html">
                                <i className="bi bi-circle"></i><span>설문 조사</span>
                            </a>
                        </li>
                        <li>
                            <a href="icons-boxicons.html">
                                <i className="bi bi-circle"></i><span>건의함</span>
                            </a>
                        </li>
                    </ul>
                </li>

                <li className="nav-item">
                    <a className="nav-link collapsed" data-bs-target="#ntc-nav" data-bs-toggle="collapse" href="#">
                        <i className="bi-globe2"></i><span>기타</span><i className="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="ntc-nav" className="nav-content collapse " data-bs-parent="#ntc-nav">
                        <li>
                            <Link to="/other/insite">
                                <i className="bi bi-circle"></i><span>인사이트</span>
                            </Link>
                        </li>
                        <li>
                            <Link to="/other/notice-list">
                                <i className="bi bi-circle"></i><span>공지사항</span>
                            </Link>
                        </li>
                        <li>
                            <Link to="icons-boxicons.html">
                                <i className="bi bi-circle"></i><span>설문 조사</span>
                            </Link>
                        </li>
                        <li>
                            <a href="icons-boxicons.html">
                                <i className="bi bi-circle"></i><span>건의함</span>
                            </a>
                        </li>
                        <li>
                            <a href="icons-boxicons.html">
                                <i className="bi bi-circle"></i><span>쪽지함</span>
                            </a>
                        </li>
                    </ul>
                </li>

            </ul>
        </aside>
    );
}