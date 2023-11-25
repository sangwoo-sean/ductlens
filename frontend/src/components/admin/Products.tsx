import { useEffect, useState } from 'react';
import { Product } from '../../App';

const baseURI = import.meta.env.VITE_API_URL;

const Products = () => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch(baseURI + '/products')
      .then((res) => res.json())
      .then((res) => {
        setProducts(res);
      });
  }, []);

  return (
    <div className="p-4">
      <table>
        <tbody>
          <tr className="border-b">
            <th>id</th>
            <th>Image</th>
            <th>name</th>
            <th>description</th>
            <th>url</th>
            <th>upvoted</th>
          </tr>
          {products.map((p) => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>
                <img src={p.imageUrl} width={80} height={80} alt="logo" />
              </td>
              <td>{p.name}</td>
              <td>{p.description}</td>
              <td>{p.url}</td>
              <td>{p.upvoted}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Products;
