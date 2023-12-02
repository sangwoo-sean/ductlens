import { useEffect, useState } from 'react';
import { Product } from '../../App';
import { APIClient } from '../../lib/APIClient.ts';

const Products = () => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    new APIClient().fetch<Product[]>('/products').then((res) => {
      setProducts(res);
    });
  }, []);

  const deleteProduct = (id: string) => {
    const confirm = window.confirm('삭제하시겠습니까?');
    if (!confirm) return;

    new APIClient()
      .fetch<void>(`/products/${id}`, { method: 'DELETE' })
      .then(() => {
        alert('삭제되었습니다.');
        setProducts((ps) => [...ps.filter((p) => p.id !== id)]);
      })
      .catch((e) => {
        console.error(e);
        alert('오류발생');
      });
  };

  return (
    <div className="p-4">
      <table>
        <tbody>
          <tr className="border-b">
            <th>No</th>
            <th>Image</th>
            <th>name</th>
            <th>description</th>
            <th>url</th>
            <th>upvoted</th>
            <th>delete</th>
          </tr>
          {products.map((p, index) => (
            <tr key={p.id}>
              <td>{index + 1}</td>
              <td>
                <img
                  src={p.imageUrl}
                  className="w-[80px] h-[80px] object-contain"
                  alt="logo"
                />
              </td>
              <td>{p.name}</td>
              <td>{p.description}</td>
              <td>{p.url}</td>
              <td className="text-center">{p.upvoted}</td>
              <td>
                <button
                  className="bg-red-400 hover:bg-red-500 text-white p-2 w-14 rounded"
                  onClick={() => deleteProduct(p.id)}
                >
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Products;
