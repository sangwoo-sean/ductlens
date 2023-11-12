import {useEffect, useState} from 'react'
import './App.css'

const baseURI = import.meta.env.VITE_API_URL;

function App() {
  const [products, setProducts] = useState<Product[]>([])

  //todo: tailwind setup
  //todo: zod
  useEffect(() => {
    fetch(baseURI + "/products")
      .then(res => res.json())
      .then(res => {
        setProducts(res)
      })
  }, [])

  type Product = {
    id: string;
    name: string;
    description: string;
    upvoted: number;
  }
  return (
    <div className="w-full flex flex-col">
      <h1>Ductlens</h1>
      <div>
        {products.map(p => (
          <div key={p.id}>
            <div>name: {p.name}</div>
            <div>desc: {p.description}</div>
            <div>upvoted: {p.upvoted}</div>
          </div>))}
      </div>
    </div>
  )
}

export default App
