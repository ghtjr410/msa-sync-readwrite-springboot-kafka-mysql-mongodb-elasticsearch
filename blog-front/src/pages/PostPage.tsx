import React, { useCallback, useRef, useState } from 'react';
import Keycloak from 'keycloak-js';
import ReactQuill from 'react-quill-new';
import 'react-quill-new/dist/quill.snow.css'; // Quill의 기본 스타일
import axios from 'axios';
import { API_GATEWAY_URL, CLOUD_FRONT_URL } from '../utils/apiUrlUtil/apiUrlUtil';

interface Props{
  keycloak: Keycloak | null;
}

const BlogEditor: React.FC<Props> = ({keycloak}) => {
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const quillRef = useRef<ReactQuill>(null);

  const imageHandler = useCallback(async ()=> {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();

    input.onchange = async () => {
      const file = input.files?.[0];
      if (file) {
        try {
          const token = keycloak?.token;
          const response = await axios.post(
            API_GATEWAY_URL()+'/api/user/images/presigned-url', 
            {
              fileName: file.name,
              fileType: file.type,
            },
            { headers: { Authorization: `Bearer ${token}` }}
          );

          const { presignedUrl, objectKey } = response.data;

          await axios.put(presignedUrl, file, {
            headers: {
              'Content-Type': file.type,
            },
          });

          const imageUrl = CLOUD_FRONT_URL() +`${objectKey}`;

          // 에디터에 이미지 삽입
          const quill = quillRef.current?.getEditor();
          if (!quill) {
            console.error("Quill editor is not initialized.");
            return;
          }
          const range = quill?.getSelection();

          if (!range) {
            quill.focus();
            const newRange = quill.getSelection();
            if (!newRange) {
              return;
            }
            quill.insertEmbed(newRange.index, 'image', imageUrl);
          } else{
            quill.insertEmbed(range.index, 'image', imageUrl);
            console.log("이미지 삽입 최종 성공");
          }
        } catch (error) {
          console.error("이미지 업로드 중 에러 발생:", error);
        }
      }
    };
  },[keycloak]);

  const modules = {
    toolbar: {
      container: [
        ["image"],
        [{ header: [1, 2, 3, 4, 5, false] }],
        ["bold", "underline"],
      ],
      handlers: {
        image: imageHandler,
      },

    },
  };

  const handleSubmit = () => {
    console.log("Title:", title);
    console.log("Content:", content);
    // 여기서 추가적인 로직을 구현할 수 있습니다 (예: 서버로 전송)
  };

  return (
    <div className="flex flex-col gap-3 max-w-[800px] mx-auto p-4 shadow-md ">
      <div className='flex flex-col gap-2 shadow-md'>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목을 입력하세요"
          className="p-3 font-bold text-4xl focus:outline-none outline-none"
        />
        <div className='bg-black border'/>
        <ReactQuill
          ref={quillRef} 
          value={content}
          onChange={setContent}
          placeholder="블로그 내용을 작성하세요..."
          className='h-[1000px]'
          // className="w-full h-88 md:h-[60vh] lg:h-[60vh] xl:h-[60vh]"
          modules={modules}
        />
      </div>
      
      <div />
      <div className='flex flex-row justify-between '>
        <button 
          className='shadow-md p-3 text-base bg-blue-500 text-white rounded cursor-pointer hover:bg-blue-700'>
          나가기
        </button>

        <button 
          onClick={handleSubmit} 
          className="shadow-md p-3 text-base bg-blue-500 text-white rounded cursor-pointer hover:bg-blue-700">
          작성
        </button>
      </div>      
    </div>    
  );
};

export default BlogEditor;