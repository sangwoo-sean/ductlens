import React, { useCallback, useState } from 'react';

const baseURI = import.meta.env.VITE_API_URL;

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
  const [name, setName] = useState('1');
  const [description, setDescription] = useState('2');
  const [imageUrl, setImageUrl] = useState('3');
  const [url, setUrl] = useState('4');

  const handleNameChange = useCallback((e) => {
    setName(e.target.value);
  }, []);

  const handleDescriptionChange = useCallback((e) => {
    setDescription(e.target.value);
  }, []);

  const handleLogoImageUrlChange = useCallback((e) => {
    setImageUrl(e.target.value);
  }, []);

  const handleWebPageUrlChange = useCallback((e) => {
    setUrl(e.target.value);
  }, []);

  const handleSubmit = useCallback(() => {
    fetch(baseURI + '/products', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name,
        description,
        imageUrl,
        url,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
      });
  }, []);

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
