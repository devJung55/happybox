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
    useFormPopup: true,
    useDetailPopup: true,
    month : DEFAULT_MONTH_OPTIONS,
    gridSelection:true,
    eventFilter:(event) => !!event.isVisible,
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
            UserCode:'그레이들',
            name: '오태양양', // 필수
            backgroundColor: '#00a9ff', // 필수
        },
        {
            id: '3',
            /* UserName: '오태양',
            name: '오태양',
            UserCode:'그레이들', */
            backgroundColor: '#fc0101',
            color : 'white',
        },
    ],
};


/* ============================================ 객체 연습=================================================== */
function Welfare(id, calendarId, body, title,start,end,UserName,isReadOnly){
	this.id = id;
	this.calendarId = calendarId;
	this.body = body;
	this.title = title;
	this.start = start;
	this.end = end;
	this.UserName = UserName;
    this.isReadOnly = true;
};

var id = "2";
var calendarId = "2";
var body = "안녕하세요";
var title = "앙기모띠";
var start = "2023-05-10";
var end = "2023-05-15";
var UserName = "강민구";
var isReadOnly = true;

/* ======================================= dateFormat  ================================================================== */
var today = new Date();

var year = today.getFullYear();
var month = ('0' + (today.getMonth() + 1)).slice(-2);
var day = ('0' + today.getDate()).slice(-2);

var dateString = year + '-' + month  + '-' + day;

/* ==================== =============================================== ===============================================*/


var welfare1 = new Welfare("1","2","아령","청국장","","2023-05-01","2023-05-05","태양");
var welfare2 = new Welfare(id,calendarId,body,title,start,end,UserName,isReadOnly);

/* =============================================================================================== */



/* 백앤드시 작성할것 */
/* 이벤트 객체 생성 -> 화면으로 Model 객체 보내서 Js로 받은 후 넘겨주는 형식 */
const event = {
    id: '1',    // iD번째 스캐줄
    calendarId: '2',    // 스케쥴의 ID(Random으로 뽑기)
    body:'안녕하세요 오태양입니다.', // 스케쥴의 설명
    title: '가정식 백반',   // 화면에 뿌려지는 이름
    state:'option:양많이',   // 현재 상태 (별필요없음)
    start: '2023-04-17 13:00',  // 시작 일
    end: '2023-04-21',  // 끝일
    UserName:'링구',    // 이거 필요없음
    isReadOnly: true,   // 이거 설정 시 편집 불가 우리는 무조건 True로 설정해줘야함
  };
  
const event1 = {
    id: '2',
    calendarId: '1',
    title: '헬스 푸드(정지영)',
    body:'안녕하세요 오태양입니다.',
    start: '2023-04-24T12:00',
    end: '2023-04-28',
    isReadOnly: true,
  };

const event2 = {
    id: '3',
    calendarId: '3',
    title: '캐밥 데이',
    body:'안녕하세요 오태양입니다.',
    start: '2023-04-10T12:00',
    end: '2023-04-14',
    isReadOnly: true,
};

const calendar = new Calendar(container, options);

console.log(calendar);


/* 이벤트 생성 */
calendar.createEvents([
      event, // event 객체
      event1,
      event2,
      welfare1,
      welfare2,
]);

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
            const { date } = moreTitle;

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
var currentDate = calendar.getDate();

$('.year').text(currentDate.getFullYear() + '년');
$('.month').text(currentDate.getMonth() + 1 + '월');

$('#calender-prev').click(() => {
    currentDate = calendar.getDate();

    var prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1);
    var prevYear = prevDate.getFullYear();
    var prevMonthIndex = prevDate.getMonth();

    $('.year').text(prevYear + '년');
    $('.month').text(prevMonthIndex + 1 + '월');

    calendar.prev();
});

$('#calender-next').click(() => {
    currentDate = calendar.getDate();

    var nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);
    var nextMonthIndex = nextDate.getMonth();
    var nextYear = nextDate.getFullYear();

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

$('.toastui-calendar-daygrid-cell').on('change',function(){
    console.log("앙들엉왔다");
    $(this).css('background-color','red');
})

