// 요소를 직접 전달하는 경우
const Calendar = tui.Calendar;
// CSS 선택자를 이용하는 경우
const container = document.getElementById('calendar');

const DEFAULT_MONTH_OPTIONS = {
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    visibleWeeksCount: 0,
    workweek: false,
    narrowWeekend: false,
    startDayOfWeek: 0,
    isAlways6Weeks: true,
    visibleEventCount: 6,
};


const options = {
    defaultView: 'month',
    /* 수정안하고 select만할때는 true, 수정 원할 시 false(Default) */
    isReadOnly: true,
    usageStatistics: false,
    useFormPopup: false,
    useDetailPopup: false,
    month: DEFAULT_MONTH_OPTIONS,
    gridSelection: true,
    eventFilter: (event) => !!event.isVisible,
    timezone: {
        zones: [
            {
                timezoneName: 'Asia/Seoul',
                displayLabel: 'Seoul',
            },
            {
                timezoneName: 'Europe/London',
                displayLabel: 'London',
            },
        ],
    },
    /* 캘린더에 들어갈 아이디 */
    calendars: [
        {
            id: '1',
            /* UserName: '오태양', */
            /* name: '정지영', */
            /* UserCode:'그레이들', */
            backgroundColor: '#03bd9e',
        },
        {
            id: '2', // 필수
            /* UserName: '오태양', */
            UserCode: '그레이들',
            name: '오태양양', // 필수
            backgroundColor: '#00a9ff', // 필수
        },
        {
            id: '3',
            /* UserName: '오태양',
            name: '오태양',
            UserCode:'그레이들', */
            backgroundColor: '#fc0101',
            color: 'white',
        },
    ],
};


/* ============================================ 객체 연습=================================================== */
function Welfare(id, calendarId, body, title, start, end, UserName, isReadOnly) {
    this.id = id;
    this.calendarId = calendarId;
    this.body = body;
    this.title = title;
    this.start = start;
    this.end = end;
    this.UserName = UserName;
    this.isReadOnly = true;
};

/* 복지관정보 */
let id = "2";
let calendarId = "2";
let body = "안녕하세요";
let title = "앙기모띠";
let start = "2023-05-10";
let end = "2023-05-15";
let UserName = "강민구";
let isReadOnly = true;

/* ======================================= dateFormat  ================================================================== */
let today = new Date();

let year = today.getFullYear();
let month = ('0' + (today.getMonth() + 1)).slice(-2);
let day = ('0' + today.getDate()).slice(-2);

let dateString = year + '-' + month + '-' + day;

/* ==================== =============================================== ===============================================*/


let welfare = new Welfare(id, calendarId, body, title, start, end, UserName, isReadOnly);

/* =============================================================================================== */


/* 백앤드시 작성할것 */
/* 이벤트 객체 생성 -> 화면으로 Model 객체 보내서 Js로 받은 후 넘겨주는 형식 */
const event = {
    id: '1',    // iD번째 스캐줄
    calendarId: '2',    // 스케쥴의 ID(Random으로 뽑기)
    body: '안녕하세요 오태양입니다.', // 스케쥴의 설명
    title: '가정식 백반',   // 화면에 뿌려지는 이름
    state: 'option:양많이',   // 현재 상태 (별필요없음)
    start: '2023-04-17 13:00',  // 시작 일
    end: '2023-04-21',  // 끝일
    UserName: '링구',    // 이거 필요없음
    isReadOnly: true,   // 이거 설정 시 편집 불가 우리는 무조건 True로 설정해줘야함
};

const event1 = {
    id: '1',
    calendarId: '1',
    title: '헬스 푸드(정지영)',
    body: '안녕하세요 오태양입니다.',
    start: '2023-04-24T12:00',
    end: '2023-04-28',
    isReadOnly: true,
};

const event2 = {
    id: '1',
    calendarId: '3',
    title: '캐밥 데이',
    body: '안녕하세요 오태양입니다.',
    start: '2023-04-10T12:00',
    end: '2023-04-14',
    isReadOnly: true,
};

const calendar = new Calendar(container, options);

console.log(calendar);

const eventList = new Array();

foodCalendars.forEach((calendar, i) => {
    let event = {
        id: i,
        calendarId: '1',
        title: calendar.foodCalendarTitle,
        body: calendar.foodCalendarDescription,
        attendees: calendar.foodList,
        start: calendar.startDate,
        end: calendar.endDate,
        isReadOnly: true,
    };
    eventList.push(event);
});

eventList.push(welfare);

/* 이벤트 생성 */
calendar.createEvents(eventList);

calendar.setTheme({
    common: {
        backgroundColor: '#fcfcfc',
        border: '1px solid #e5e5e5',
        dayName: {
            color: '#666',
        },
        saturday: {
            color: 'rgba(64, 64, 255, 0.5)',
        },
        today: {
            color: 'white',
        },
    },
});

calendar.setOptions({
    template: {
        monthMoreTitleDate(moreTitle) {
            const {date} = moreTitle;

            return `<span>${date}</span>`;
        },
    },
});

/* 포멧 설정(custom) */

/* function formatTime(time) {
    const hours = `${time.getHours()}`.padStart(2, '0');
    const minutes = `${time.getMinutes()}`.padStart(2, '0');
  
    return `${hours}:${minutes}`;
  }
  
  calendar.setOptions({
    template: {
      time(event) {
        const { start, end, title } = event;
  
        return `<span style="color: white;">${formatTime(start)}~${formatTime(end)} ${title}</span>`;
      },
      allday(event) {
        return `<span style="color: gray;">${event.title}</span>`;
      },
    },
  }); */


// 팝업을 통해 이벤트를 생성
calendar.on('beforeCreateEvent', (eventObj) => {
    calendar.createEvents([
        {
            ...eventObj,
            id: uuid(),
        },
    ]);
});

// calendar.on('clickEvent', ({ event }) => {
//     const el = document.getElementById('clicked-event');
//     el.innerText = event.title;
// });


// ==================================================== 캘린더 버튼
let currentDate = calendar.getDate();

$('.year').text(currentDate.getFullYear() + '년');
$('.month').text(currentDate.getMonth() + 1 + '월');

$('#calender-prev').click(() => {
    currentDate = calendar.getDate();

    let prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1);
    let prevYear = prevDate.getFullYear();
    let prevMonthIndex = prevDate.getMonth();

    $('.year').text(prevYear + '년');
    $('.month').text(prevMonthIndex + 1 + '월');

    calendar.prev();
});

$('#calender-next').click(() => {
    currentDate = calendar.getDate();

    let nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);
    let nextMonthIndex = nextDate.getMonth();
    let nextYear = nextDate.getFullYear();

    $('.year').text(nextYear + '년');
    $('.month').text(nextMonthIndex + 1 + '월');

    calendar.next();
});

$('#today').click(() => {
    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth() + 1;
    const day = now.getDate();
    const hour = now.getHours();
    const minute = now.getMinutes();
    const second = now.getSeconds();

    $('.year').text(year + '년');
    $('.month').text(month + '월');
    $('.day').text(day + '일');
    $('.hour').text(hour + '시');
    $('.minute').text(minute + '분');

    calendar.today();
});


/* ======================================= 이벤트 생성 ========================================== */

function dateConverter(date) {
    // 버그로 인해 수동으로 했음...
    const months = {
        Jan: "01",
        Feb: "02",
        Mar: "03",
        Apr: "04",
        May: "05",
        Jun: "06",
        Jul: "07",
        Aug: "08",
        Sep: "09",
        Oct: "10",
        Nov: "11",
        Dec: "12",
    }

    let day = date.toString().split(" ")[2];
    let month = months[date.toString().split(" ")[1]];
    let year = date.getFullYear();

    return `${year}-${month}-${day}`;
}

$('.toastui-calendar-daygrid-cell').on('change', function () {
    console.log("앙들엉왔다");
    $(this).css('background-color', 'red');
});

calendar.on("clickEvent", function (e) {
    let text = ``;

    console.log(e);
    $("#startDate").text(dateConverter(e.event.start.d.d));
    $("#endDate").text(dateConverter(e.event.end.d.d));

    $(".modal-name h3").text(e.event.title);
    $(".modal-name p").text(e.event.body);

    let attendees = e.event.attendees;

    for (let i = 0; i < attendees.length; i++) {
        let filePath = `/image/display/${attendees[i].filePath}/t_${attendees[i].fileUuid}_${attendees[i].fileOrgName}`;
        text += `
            <div class="food">
                <img src="/img/welfare/welfare_img_default.png" alt="">
                <span>${attendees[i].foodName}</span>
            </div>
        `
    }

    $(".food-list").html(text);

    $("#cart-modal").css("display", "block");
});

/* ========= 장바구니 모달창 ========= */
// 닫기 버튼을 클릭했을 때
$(".close").on("click", function () {
    $("#cart-modal").css("display", "none");
});


// 모달창 외부를 클릭했을 때
$(window).on("click", function (event) {
    if ($(event.target).is('.modal')) {
        $("#cart-modal").css("display", "none");
    }
});

