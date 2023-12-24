# Projeye Dair Detaylar

### Genel Detaylar

- Business katmanlarında if condition sayısını azaltmak için businesslarda @Autowired ile IOC’den enjekte edilen Rules sınıfları bulunmaktadır. Kurallar @Service anotasyonu ile IOC’ye dahildir.
- Frontend’de kolaylık olması açısından ResponseEntity değil, Result yapısı oluşturulmuş ve kullanılmıştır.
- Neredeyse tüm Dto-Entity ilişkileri ModelMapper ile maplenmiştir.
- ModelMapper ihtiyaçlar doğrultusunda konfigüre edilmiştir.
- Request-Response yapısı kullanılmıştır.
- Tüm ID’ler benzersiz UUID’lerin Stringlere dönüştürülmesiyle otomatik olarak oluşturulur.
- Veri Tipleri olabildiğince gerçek projelere yakın tiplerde oluşturulmuştur. Price değeri BigDecimal veya az önce bahsettiğim ID gibi.
- Uçak biletlerinin iniş/biniş saat ve gün bilgilerinin hangi ülkenin saatine göre olduğu önem arz ettiği gözetildiğinden zaman veri tiplerinde her zaman ZonedDateTime kullanılmıştır.

## AIRPORT ÖZELLİKLERİ

- Yalnızca Admin ve Root kullanıcıların görebileceği toplamda 4 adet endpointten oluşmaktadır.
- Endpointler Update.Delete.GetAll ve Add işlemlerini gerçekleştirir.
- Bir havalimanının yıkılması durumu oldukça nadirdir. Yine de pist bakımı gibi sebeplerden ötürü havalimanı kullanılamaz halde olabileceğinden delete metotu oluşturulmuştur. Delete metotu “soft delete” yapar. Dolayısıyla DeleteMapping yerine PutMapping kullanılmıştır. IsActive sütunu oluşturulmuştur ve eğer silme isteği yapılırsa isActive kolonu false değere dönüştürülmektedir.
- Aynı havalimanı tekrar eklenemez.
- Havalimanı eklenirken name alanı Büyük harflere çevrilerek veritabanına kaydedilir. Türkçe harf uyumludur.

## FLIGHT OZELLIKLERI

Flight metotları Yalnızca Admin ve Root kulanıcıların erişebileceği Update, Add, GetAll, Delete metotlarının yanısıra User kullanıcılarının da görebileceği SearchApi metodu ve erişime kapalı fetchFromMockApi metoduyla birlikte toplamda 6 adet fonksiyondan oluşmaktadır.

##### Add Özellikleri

- Gidiş veya dönüş tarihi geçmiş bir tarihte bir uçuş eklenemez.
- Dönüş tarihi, gidiş tarihinden önce olamaz.
- Fiyat ücretsiz veya aşağı bir değer olamaz.
- Olmayan bir havalimanına uçuş eklenemez. (Kontroller ID üstünden yapılır.)
- Havalimanı ID’si String olmayan bir değer olamaz.
- Kalkış havalimanı ve iniş havalimanı aynı olamaz.

##### Update Özellikleri

- Bir uçuşun kalkış ve iniş yerinin değişmesi pek olası olmadığından yalnızca tarih değerleri ve fiyat bilgisi güncellenebilir.
- Olmayan bir uçuşun ID’si girilemez.
- Tarih ve Fiyat şartları Ekleme metoduyla aynı logic kurallarını içermektedir.

##### Delete Özellikleri

- Yalnızca ID alır ve Hard Delete yapar.
- Olmayan bir uçuşun ID’si girilemez.
- String olmayan bir değer girilemez.

##### GetAll Özellikleri

- Klasik getAll metodu, ModelMapper kullanarak gelen Flight nesnelerini mapler ve List of DTO olarak sonuç döner.

_SearchApi özellikleri aşağıda ayrıca verilmiştir._

---

## SearchApi Özellikleri

- SearchApi geçmişte arama yapamaz !! (güvenlik açığı oluşturur)
- SearchApi verilen tarihten 24 saat öncesi ve sonrasını da kapsayarak 48 saatlik bir arama gerçekleştirerek kullanıcıya olası alternatifleri sunar. (Hem varış tarihi hem de dönüş tarihi için).
- SearchApi yalnızca gidiş isteği olan bir kişi için dönüşü olan uçuşları da getirir. Ancak bu durum tersinim yapmaz.
- SearchApi ile A noktasından B noktasına gidiş bileti arattığınızı düşünelim. Eğer A noktasından B noktasına direkt gidiş yoksa ancak. B’den A’ya gidiş dönüş bileti varsa : dönüş seçeneği gidiş seçeneği olarak gelir ve fiyat gidiş dönüş fiyatının yarısına düşer (mesafe hesaplanarak customize yapılabilir, örnek olması açısından yarıya düşürülmüştür).
- SearchApi eğer dönüş tarihi verildiyse çift yönlü arama yaparken. Yalnızca gidiş tarihi verildiğinde tek taraflı arama yapar.
- SearchApi bir uçuş arama algoritması olduğundan dolayı FlightController içerisinde (“/searchapi”) endpointinde oluşturulmuştur.
- Büyük küçük harf dönüşümü duyarsızdır. Örneğin dropboxta ismi yanlışlıkla antalyA şeklinde yazılmış bir veriyi “ANTALYA” gibi kabul eder ve aramayı buna göre yapar.
- Türkçe harf kabul eder.

---

## Authentication

- Inmemory değil DataSource kullanılarak yapılmıştır. Tüm kullanıcılar Database’de kayıtlıdır. Varsayılan kullanıcı yoktur ve bu yüzden application.properties içerisinde kullanıcı bilgisi bulunamaz.

- _**Çok önemli not: Eğer applikasyonu github’dan clonelarsanız. Root özelliklerine erişmek için Database’de username’i batuhan şifresi ise herhangi bir Bcrypt algoritması ile şifrelenmiş celebi değeri oluşturunuz.**_

- Swagger UI herkes tarafından görülebilir. Ancak endpointlere erişim için authentication zorunludur.
- Klasik HttpBasic Authentication kullanılmıştır.
- Hiyerarşi Root > Admin > User şeklindedir.

## APPUSER ÖZELLİKLERİ

- Yalnızca Root kullanıcının erişebileceği 3 endpointten oluşmaktadır.
- Username Unique(benzersiz) olmalıdır. Role Admin veya User olabilir. Admin veya user büyük küçük harf duyarsızdır (usEr veya aDMiN gibi yazılar kabul edilecektir ve kaydedilmeden önce büyük harflere dönüştürülecektir). Password kayıt edilirken şifreleme algoritması olarak Bcrypt algoritması kullanılmaktadır.
- Olmayan bir kullanıcı Silinemez. Silme işlemi isActive kolonu kullanılarak Soft delete şeklinde yapılmaktadır.

---

## FETCH from MOCK API

- Her gün saat 11.00’de bir third party Rest API’a istek yapılarak gelen CreateFlightRequest[] arrayi gövdeden çekilir ve Flight varlığına maplenir.
- Bu metod her çalıştığında appLog dosyasına düşük seviyede LOGLANIR.
- Metot FlightBusiness içerisindedir ve başka bir Component’tan çağırılmaktadır. IOC’ye dahildir ve program çalıştığı takdirde sürekli istek yapacaktır.
