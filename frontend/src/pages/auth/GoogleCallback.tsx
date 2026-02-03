import { useEffect } from 'react';

export default function GoogleCallback() {
    useEffect(() => {
        const code = new URLSearchParams(window.location.search).get('code');
        console.log('구글 인가 코드:', code);
    }, []);

    return <div>구글 로그인 테스트 중...</div>;
}
