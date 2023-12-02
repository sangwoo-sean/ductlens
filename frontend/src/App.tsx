import { useEffect, useState } from 'react';
import './App.css';
import { ProductItem } from './components/ProductItem';
import { APIClient } from './lib/APIClient.ts';

export type Product = {
  id: string;
  name: string;
  description: string;
  upvoted: number;
  imageUrl: string;
  url: string;
};

function App() {
  const [products, setProducts] = useState<Product[]>([]);

  //todo: zod
  useEffect(() => {
    new APIClient().fetch<Product[]>('/products').then((res) => {
      setProducts(res);
    });
  }, []);

  const onUpvoted: (productId: String) => void = (productId: String) => {
    setProducts((ps) => [
      ...ps.map((p) => ({
        ...p,
        upvoted: p.upvoted + (p.id === productId ? 1 : 0),
      })),
    ]);
  };

  return (
    <div className="flex flex-col items-center min-h-[100vh] min-w-[320px]">
      <div className="pt-[2rem] pb-[3rem]">
        <h1>Ductlens</h1>
      </div>
      <div className="flex flex-col gap-2">
        {products.map((p) => (
          <ProductItem key={p.id} {...p} onUpvoted={onUpvoted} />
        ))}
      </div>
    </div>
  );
}

export default App;
