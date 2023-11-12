import { useEffect, useState } from 'react';
import './App.css';

const baseURI = import.meta.env.VITE_API_URL;

type Product = {
  id: string;
  name: string;
  description: string;
  upvoted: number;
  imageUrl: string;
  url: string;
}

const ProductItem: React.FC<Product> = ({ id, name, description, upvoted, imageUrl, url }) => {
  return (
    <div className='flex rounded border hover:border-black' onClick={() => window.open(url)}>
      <div>
        <img src={imageUrl} alt='imageUrl' />
      </div>
      <div className='w-[20rem] p-3'>
        <div>{name}</div>
        <div>{description}</div>
      </div>
      <div className='flex items-center'>
        <div>upvoted: {upvoted}</div>
      </div>
    </div>
  );
};

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
    <div className='flex flex-col items-center'>
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
