import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import PhaseColumn from './PhaseColumn.jsx';

const StoryBoardArea = styled.div`
  display: flex;
  flex-direction: row;
`;

export default function StoryBoard() {
  { /* State */ }
  const [phases, setPhases] = useState([]);
  const [statuses, setStatuses] = useState([]);
  const [isLoadingPhases, setIsLoadingPhases] = useState(true);
  const [isLoadingStatuses, setIsLoadingStatuses] = useState(true);

  useEffect(() => {
    const statusurl = "/api/status/findall?limit=10";
    fetch(statusurl)
      .then((response) => response.json())
      .then((json) => {
        json.sort((a, b) => ((a.id > b.id) ? 1 : -1));
        setStatuses(json);
        setIsLoadingStatuses(false);
      });

    const url = "/api/phase/findall?limit=10";
    fetch(url)
      .then((response) => response.json())
      .then((json) => {
        json.sort((a, b) => ((a.id > b.id) ? 1 : -1));
        setPhases(json);
        setIsLoadingPhases(false);
      });
  }, []);

  return (
    <StoryBoardArea>
      {/* This is where we will have columns for workflow states */}
      {(isLoadingPhases || isLoadingStatuses) ? 'Takes 20 seconds to warm up the AWS Lambda...' : phases.map((phase, index) => (
        phase.name == 'done' ? null :
          <PhaseColumn key={index} id={index} statuses={statuses} phase={phase}></PhaseColumn>
      ))}
    </StoryBoardArea>
  );
}

