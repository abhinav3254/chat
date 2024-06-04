import React, { useState } from 'react';
import axios from 'axios';
import './App.css';
import { Word } from './interfaces/Word';
import audioSvg from './images/audio.svg';
import rightArrow from './images/right-arrow.svg';

const App = () => {
  const [searchText, setSearchText] = useState('');
  const [response, setResponse] = useState<Word[] | null>(null);

  // Setting up the default URL
  axios.defaults.baseURL = 'https://api.dictionaryapi.dev/api/v2/entries/en/';

  const findInDictionary = async () => {
    try {
      const res = await axios.get<Word[]>(searchText);
      if (res.status === 200) {
        console.log(res.data);
        setResponse(res.data);
      }
    } catch (error) {
      alert('Not found');
    }
  };

  return (
    <div>
      <div className='search'>
        <input
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          type='text'
          placeholder='Search here...'
        />
        <button onClick={findInDictionary}>Search</button>
      </div>
      <div className="dataConatiner">
        {response && response.map((word, index) => (
          <div key={index} className='DataSingle'>
            <h2 className='words'>{word.word}</h2>
            <p className='phonetic'>{word.phonetic}</p>

            <div className='phonetics-container'>
              {word.phonetics.map((data, key) => (
                <div key={key} className='phonetics'>
                  <div className='phonetics-row'>
                    <p>{data.text}</p>
                    <img src={audioSvg} alt="audio" />
                  </div>
                  {data.license &&
                    <a href={data.sourceUrl} target='_blank'>{data.license.name}</a>
                  }
                </div>
              ))}
            </div>

            {
              word.meanings.map((data, key) => (
                <div key={key} className='meanings'>
                  <h4>Meaning</h4>
                  <p>{data.partOfSpeech}</p>

                  {data.synonyms.length > 0 && (
                    <div>
                      <h4>Synonyms</h4>
                      <div className="synonyms">
                        {data.synonyms.map((syn, key) => (
                          <p className='synonymsElement' key={key}>{syn}</p>
                        ))}
                      </div>
                    </div>
                  )}

                  <h4>Examples</h4>
                  <div className='meaningsContainer'>
                    {data.definitions.map((data, key) => (
                      <div key={key}>
                        <div className="Indexing">
                          <img src={rightArrow} alt="" />
                          <p>{data.definition}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              ))
            }

          </div>
        ))}
      </div>
    </div >
  );
};

export default App;
