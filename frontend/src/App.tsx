import { useEffect, useState } from 'react';
import './App.css';
import { ProductItem } from './components/ProductItem';

const baseURI = import.meta.env.VITE_API_URL;

export type Product = {
  id: string;
  name: string;
  description: string;
  upvoted: number;
  imageUrl: string;
  url: string;
}

function App() {
  const [products, setProducts] = useState<Product[]>([]);

  //todo: zod
  useEffect(() => {
    fetch(baseURI + '/products')
      .then(res => res.json())
      .then(res => {
        setProducts(res);
      });
  }, []);

  return (
    <div className='flex flex-col items-center min-h-[100vh] min-w-[320px]'>
      <div className='pt-[2rem] pb-[3rem]'>
        <h1>Ductlens</h1>
      </div>
      <div className='flex flex-col gap-2'>
        {products.map(p => <ProductItem key={p.id} {...p} />)}
      </div>
    </div>
  );
}

export default App;
