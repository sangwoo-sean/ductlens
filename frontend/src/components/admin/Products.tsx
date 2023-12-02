import {useEffect, useState} from 'react';
import {Product} from '../../App';

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

  const deleteProduct = (id: string) => {
    const confirm = window.confirm("삭제하시겠습니까?")
    if (!confirm) return;

    fetch(baseURI + `/products/${id}`, {
      method: "DELETE"
    })
      .then((res) => {
        if (res.ok) {
          alert("삭제되었습니다.");
          setProducts(ps => [...ps.filter(p => p.id !== id)])
        } else {
          console.error(res);
          alert("오류발생");
        }
      });
  }

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
              <img src={p.imageUrl} className="w-[80px] h-[80px] object-contain" alt="logo"/>
            </td>
            <td>{p.name}</td>
            <td>{p.description}</td>
            <td>{p.url}</td>
            <td className="text-center">{p.upvoted}</td>
            <td>
              <button className="bg-red-400 hover:bg-red-500 text-white p-2 w-14 rounded"
                      onClick={() => deleteProduct(p.id)}>삭제
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
