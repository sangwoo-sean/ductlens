import React from 'react';
import { APIClient } from '../lib/APIClient.ts';

export type ProductItemProps = {
  id: string;
  name: string;
  description: string;
  upvoted: number;
  imageUrl: string;
  url: string;
  onUpvoted: (productId: String) => void;
};

const ProductItem: React.FC<ProductItemProps> = ({
  id,
  name,
  description,
  upvoted,
  imageUrl,
  url,
  onUpvoted,
}) => {
  const clickUpvote = (e: React.MouseEvent<HTMLButtonElement>) => {
    new APIClient()
      .fetch<void>(`/products/${id}/upvote`, { method: 'POST' })
      .then(() => {
        onUpvoted(id);
      });
    e.stopPropagation();
  };

  return (
    <div
      className="flex rounded border hover:border-black cursor-pointer"
      onClick={() => window.open(url)}
    >
      <div className="flex justify-center items-center p-1">
        <img src={imageUrl} alt="imageUrl" width={150} height={150} />
      </div>
      <div className="w-[25rem] min-h-[10rem] p-3">
        <div className="font-bold">{name}</div>
        <div className="mt-2 text-sm">{description}</div>
      </div>
      <div className="flex items-center mx-2">
        <div className="flex flex-col items-center">
          <button onClick={clickUpvote}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              height="24"
              viewBox="0 -960 960 960"
              width="24"
            >
              <path d="m80-160 400-640 400 640H80Zm144-80h512L480-650 224-240Zm256-205Z" />
            </svg>
            <div>{upvoted}</div>
          </button>
        </div>
      </div>
    </div>
  );
};

export { ProductItem };
