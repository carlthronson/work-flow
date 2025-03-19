// `app/task-board/page.js` is the UI for the `/task-board/` URL
'use client'
import React, { useState, useEffect } from 'react';
import 'react-calendar/dist/Calendar.css';
import Month from '../../components/Month';
import Header from '../../components/Header';
import moment from 'moment';
import './style.css';

export default function Page() {
  let today = moment();
  const [someDay, setSomeDay] = useState(today);

  const prev = () => {
    setSomeDay(someDay.clone().subtract(1, 'months'));
  };

  const next = () => {
    setSomeDay(someDay.clone().add(1, 'months'));
  };

  return <div className='calendar-container'>
    <Header someMoment={someDay} prev={prev} next={next}></Header>
    <Month someMoment={someDay} today={today}></Month>
  </div>
}
