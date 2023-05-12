const Calendar = tui.Calendar;
cal = new Calendar('#calendar', {
    defaultView: 'month',
    useCreationPopup: false,
    useDetailPopup: false,
    theme: MONTHLY_CUSTOM_THEME,
    timezones: {
        timezoneOffset: 540,
        // displayLabel: 'GMT+09:00',
        tooltip: 'Seoul'
    },
    calendars: [
        {
            id: '1',
            name: '개인',
            color: '#ffffff',
            bgColor: '#9e5fff',
            dragBgColor: '#9e5fff',
            borderColor: '#9e5fff'
        },
        {
            id: '2',
            name: '회사',
            color: '#00a9ff',
            bgColor: '#00a9ff',
            dragBgColor: '#00a9ff',
            borderColor: '#00a9ff'
        },
        {
            id: '3',
            name: '휴일',
            color: '#ff5583',
            bgColor: '#ff5583',
            dragBgColor: '#ff5583',
            borderColor: '#ff5583'
        }
    ]

});

var MONTHLY_CUSTOM_THEME = {
    // month header 'dayname'
    'month.dayname.height': '42px',
    'month.dayname.borderLeft': 'none',
    'month.dayname.paddingLeft': '8px',
    'month.dayname.paddingRight': '0',
    'month.dayname.fontSize': '16px',
    'month.dayname.backgroundColor': 'inherit',
    'month.dayname.fontWeight': 'normal',
    'month.dayname.textAlign': 'left',
    'month.dayname.borderBottom': '1px solid #e5e5e5',

    // month day grid cell 'day'
    'month.holidayExceptThisMonth.color': '#f3acac',
    'month.dayExceptThisMonth.color': '#bbb',
    'month.weekend.backgroundColor': '#ffba6b2b',
    'month.day.fontSize': '16px',

    // month schedule style
    'month.schedule.borderRadius': '5px',
    'month.schedule.height': '18px',
    'month.schedule.marginTop': '2px',
    'month.schedule.marginLeft': '10px',
    'month.schedule.marginRight': '10px',

    // month more view
    'month.moreView.boxShadow': 'none',
    'month.moreView.paddingBottom': '0',
    'month.moreView.border': '1px solid #9a935a',
    'month.moreView.backgroundColor': '#f9f3c6',
    'month.moreViewTitle.height': '28px',
    'month.moreViewTitle.marginBottom': '0',
    'month.moreViewTitle.backgroundColor': '#f4f4f4',
    'month.moreViewTitle.borderBottom': '1px solid #ddd',
    'month.moreViewTitle.padding': '0 10px',
    'month.moreViewList.padding': '10px'
};