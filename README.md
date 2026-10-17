# integrity-play-lib

مكتبة أندرويد عامة ومستقلة لتجريد واستدعاء **Google Play Integrity API** لحماية التطبيقات من التلاعب والهندسة العكسية وفحص أصالة الأجهزة.

## المميزات
- استدعاء غير معطل لـ `IntegrityManager` باستخدام Kotlin Coroutines.
- إنشاء طلبات التحقق وطلب الرموز (`Integrity Token`) للتحقق من جهة الخادم.
- محرك محاكاة كامل (`FakeIntegrityEngine`) للاختبارات ووضع التطوير.
