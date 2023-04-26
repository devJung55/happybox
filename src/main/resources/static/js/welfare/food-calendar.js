// 요소를 직접 전달하는 경우
const Calendar = tui.Calendar;
// CSS 선택자를 이용하는 경우
const container = document.getElementById('calendar');
const options = {
    defaultView: 'month',
    isReadOnly: true,
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
            id: 'cal1',
            name: '개인',
            backgroundColor: '#03bd9e',
        },
        {
            id: 'cal2',
            name: '직장',
            backgroundColor: '#00a9ff',
        },
    ],
};

const calendar = new Calendar(container, options);

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

    calendar.today();
});
