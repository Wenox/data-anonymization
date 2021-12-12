import React from 'react';
import Main from './pages/main';
import {QueryClient, QueryClientProvider} from 'react-query';
import {BrowserRouter} from "react-router-dom";

const queryClient = new QueryClient();

function App() {
    return (
        <BrowserRouter>
            <QueryClientProvider client={queryClient}>
                <Main/>
            </QueryClientProvider>
        </BrowserRouter>
    );
}

export default App;
