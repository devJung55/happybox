/* import */

// 요소를 직접 전달하는 경우
const Calendar = tui.Calendar;
// CSS 선택자를 이용하는 경우
const container = document.getElementById('calendar');

const DEFAULT_MONTH_OPTIONS = {
    dayNames: ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'],
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
    calendars: [
        {
            id: 'welfare',
            name: 'ty',
            backgroundColor: '#03bd9e',
        },
        {
            id: 'cal1',
            name: 'ty',
            backgroundColor: '#00a9ff',
        },
    ],
};

/* 백앤드시 작성할것 */
const event = {
    id: 'cal1',
        calendarId: 'cal1',
        title: '가정식 백반',
        start: '2023-04-17 13:00',
        end: '2023-04-21',
        isReadOnly: true,
  };
  
const event1 = {
    id: 'cal1',
        calendarId: 'cal1',
        title: '헬스 푸드(정지영)',
        start: '2023-04-24T12:00',
        end: '2023-04-28',
        isReadOnly: true,
  };

const calendar = new Calendar(container, options);


/* 이벤트 생성 */
calendar.createEvents([
      event, // event 객체
      event1,
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

calendar.on('clickEvent', ({ event }) => {
    const el = document.getElementById('clicked-event');
    el.innerText = event.title;
});


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

// $('.toastui-calendar-daygrid-cell').on('change',function(){
//     console.log("앙들엉왔다");
//     $(this).css('background-color','red');
// })