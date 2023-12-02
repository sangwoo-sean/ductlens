import React, { useCallback, useState } from 'react';
import { APIClient } from '../../lib/APIClient.ts';

type CustomTextInputProps = {
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};
const CustomTextInput: React.FC<CustomTextInputProps> = React.memo(
  ({ name, value, onChange }) => {
    return (
      <div className="p-2 flex items-center">
        <label className="font-bold w-[120px]" htmlFor="name">
          {name}
        </label>
        <input
          className="border rounded p-2"
          type="text"
          id={name}
          value={value}
          onChange={onChange}
          placeholder={name}
          autoComplete="off"
        />
      </div>
    );
  },
);

const ProductAddForm = () => {
  const [name, setName] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [imageUrl, setImageUrl] = useState<string>('');
  const [url, setUrl] = useState<string>('');

  const handleNameChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setName(e.target.value);
    },
    [],
  );

  const handleDescriptionChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setDescription(e.target.value);
    },
    [],
  );

  const handleLogoImageUrlChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setImageUrl(e.target.value);
    },
    [],
  );

  const handleWebPageUrlChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setUrl(e.target.value);
    },
    [],
  );

  const handleSubmit = useCallback(() => {
    new APIClient()
      .fetch<void>('/products', {
        method: 'POST',
        body: {
          name,
          description,
          imageUrl,
          url,
        },
      })
      .then(() => {
        setName('');
        setDescription('');
        setImageUrl('');
        setUrl('');
        alert('등록 성공');
      })
      .catch(() => {
        alert('오류발생');
      });
  }, [name, description, imageUrl, url]);

  return (
    <div className="p-2">
      <CustomTextInput value={name} onChange={handleNameChange} name="name" />
      <CustomTextInput
        value={description}
        onChange={handleDescriptionChange}
        name="description"
      />
      <CustomTextInput
        value={imageUrl}
        onChange={handleLogoImageUrlChange}
        name="logo image url"
      />
      <CustomTextInput
        value={url}
        onChange={handleWebPageUrlChange}
        name="web page url"
      />
      <button
        onClick={handleSubmit}
        className="bg-blue-500 text-white px-3 py-1.5 rounded text-[14px]"
      >
        등록
      </button>
    </div>
  );
};

export default ProductAddForm;
