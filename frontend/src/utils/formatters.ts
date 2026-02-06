/**
 * 데이터 포맷터 유틸리티
 */

/**
 * 동물 나이 포맷팅
 * @param age 숫자 또는 연도 문자열 (예: 2026, "202660")
 * @returns 포맷팅된 문자열 (예: "2026년생")
 */
export const formatAge = (age: number | string | null | undefined): string => {
    if (age === null || age === undefined || age === '') {
        return '나이미상';
    }

    const ageStr = String(age);
    // 앞 4자리 숫자만 추출
    const yearMatch = ageStr.match(/^\d{4}/);

    if (yearMatch) {
        return `${yearMatch[0]}년생`;
    }

    // 숫자가 아니거나 형식이 다른 경우 그대로 반환하거나 "미상" 처리
    return isNaN(Number(ageStr)) ? '나이미상' : `${ageStr}년생`;
};
