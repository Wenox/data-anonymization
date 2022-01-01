import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './app';
import { toast } from 'react-toastify';
import './api/axios.config';

toast.configure();

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root'),
);
