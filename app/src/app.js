import { RouterProvider } from 'react-router-dom';
import './app.css';
import { router } from './router';
import { GlobalUserProvider } from './context/user.context';

function App() {
  return (
    <div className='scrollbar app-container'>
      <GlobalUserProvider>
        <RouterProvider router={router} />
      </GlobalUserProvider>
    </div>
  );
}

export default App;
