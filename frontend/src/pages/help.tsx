import {
    Divider,
    Icon,
    Paper,
    SvgIconTypeMap,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import accessibilityImg from './accessibility.png';

import React from "react";
import * as Icons from "@mui/icons-material";

import {Typography} from '@mui/material';
import {styled} from '@mui/system';
import ListItem from "@mui/material/ListItem";
import List from "@mui/material/List";
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';


const BoldTableCell = styled(TableCell)(({theme}) => ({
    // @ts-ignore
    fontWeight: theme.typography.fontWeightBold,
}));

const StyledTableCell = styled(TableCell)(({theme}) => ({
    borderBottom: '1px solid rgba(224, 224, 224, 1)',
    padding: theme.spacing(1),
}));

const IconTableCell = styled(TableCell)(({theme}) => ({
    width: '30px',
    textAlign: 'center',
    borderBottom: '1px solid rgba(224, 224, 224, 1)',
    padding: theme.spacing(1),
}));

const requirements = [
    {
        requirement: 'Serwis może być zrealizowany na bazie HTML 4.01 lub XHTML (dopuszczalne jest też\n' +
            'użycie XML+XSLT). Strony muszą być utworzone poprawnie i mieć poprawne deklaracje\n' +
            'typu – będą przepuszczane przez walidator w ramach testu.',
        description: 'Platforma korzysta z technologii HTML 5, która wprowadza m.in. dodatkowe semantyczne elementy, które pozytywnie wpływają na wymaganie związane z dostępnością dla niepełnosprawnych. Platforma korzysta z technologii React, która może łamać kompatybilność z walidatorem.',
        statusIcon: <HelpOutlineIcon style={{ color: 'red', fontSize: 64 }} />,
    },
    {
        requirement: 'Wymagane jest nietrywialne formatowanie przy użyciu CSS.',
        description: 'Platforma korzysta z licznych reguł CSS. Dodatkowo korzysta z technologii SCSS (Sass).',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    },
    {
        requirement: 'Kompatybilność i „łagodna degradacja” - serwis powinien funkcjonować poprawnie i\n' +
            'wyglądać dobrze we wszystkich popularnych przeglądarkach (najnowsze wersje), a\n' +
            'korzystanie z serwisu powinno być też możliwe – choć zapewne nie bez zakłóceń w rodzaju\n' +
            'nieudanego formatowania – w przeglądarkach starszych lub bardziej prymitywnych pod\n' +
            'względem możliwości prezentacyjnych (np. lynx). W przypadku podjęcia decyzji o użyciu\n' +
            'XHTML 1.1 wystarczy obsługa przez przeglądarki respektujące ten standard.',
        description: 'Platforma działa poprawnie na współcześnie używanych przeglądarkach i urządzeniach mobilnych. Platforma skaluje się do rozmiaru okna urządzenia.',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    },
    {
        requirement: 'Dostępność dla niepełnosprawnych – w szczególności powinna być możliwa nawigacja\n' +
            'przy użyciu oprogramowania czytającego (a zatem wszystkie istotne obrazki powinny mieć\n' +
            'teksty alternatywne, itp.). Oczywiście biorąc pod uwagę skalę projektu nie możemy\n' +
            'przesadzać z rozmachem – np. nie jest wymagane dostarczenie reguł CSS dla medium aural.',
        description: 'Platforma korzysta z dostępności dla niepełnosprawnych w wybranych miejscach. Szczegółowy przykład zaprezentowano poniżej.',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    },
    {
        requirement: 'Poza (X)HTML i CSS serwis powinien wykorzystywać co najmniej jedną wybraną\n' +
            'technologię spośród przedstawionych na wykładzie (np. skrypty po stronie serwera i/lub\n' +
            'klienta, ciasteczka, bazy danych, web serwisy, SSL... - do wyboru)',
        description: 'Platforma korzysta z wszystkich wymienionych technologii. Certyfikat SSL nie jest użyty w aktualnym wdrożeniu (z pominięciem ngrok reverse proxy).',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    },
    {
        requirement: 'Serwis powinien zawierać dobrze przemyślany, wygodny i spójny system nawigacji. Co\n' +
            'więcej, serwis powinien być na tyle duży, aby ten system miał sens – przypominamy o\n' +
            'możliwości posiłkowania się wszelkiego rodzaju „materiałem zastępczym”.',
        description: 'Platforma spełnia wymagania funkcjonalne dla platformy anonimizacji baz danych.',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    },
    {
        requirement: 'Serwis musi zawierać stronę poświęconą opisowi samego serwisu – jakich technologii\n' +
            'użyto, jakie były założenia przy planowaniu nawigacji, oraz dlaczego autor uważa, że strona\n' +
            'spełnia wymagania wymienione w tym dokumencie.',
        description: 'Platforma zawiera podstronę /help z wymaganym opisem.',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    },
    {
        requirement: 'Przypominamy, że projektowany jest serwis internetowy, a więc strona, z której\n' +
            'teoretycznie ktoś miałby korzystać. Nieczytelne zestawienia kolorów, mikroskopijne\n' +
            'czcionki, migające irytująco obrazki itp. nie powinny występować – prosimy utworzyć\n' +
            'stronę, która nie tyle próbuje pochwycić uwagę gościa (choć to pożądane), ile zaoferować\n' +
            'mu wygodny dostęp do poszukiwanych treści.',
        description: 'Platforma w mojej subiektywnej ocenie spełnia to wymaganie.',
        statusIcon: <CheckCircleOutlineIcon style={{ color: 'green', fontSize: 64 }} />,
    }
    // Dodaj kolejne wymagania tutaj
];


const Help = () => {
    const data = [
        {
            category: 'Technologie',
            items: [
                {
                    subcategory: 'Klient',
                    technologies: [
                        {
                            name: 'TypeScript',
                            description: 'Język programowania z silnym typowaniem, nadbudowa nad JavaScript',
                            iconName: 'Code' as keyof typeof Icons,
                            color: '#007acc',
                        },
                        {
                            name: 'React.js',
                            description: 'Biblioteka do tworzenia interaktywnych interfejsów użytkownika',
                            iconName: 'Web' as keyof typeof Icons,
                            color: '#61dafb',
                        },
                        {
                            name: 'React Router',
                            description: 'Biblioteka do zarządzania nawigacją w aplikacji React',
                            iconName: 'DeviceHub' as keyof typeof Icons,
                            color: '#ca4245',
                        },
                        {
                            name: 'React Hook Form',
                            description: 'Lekka biblioteka do obsługi formularzy w aplikacji React',
                            iconName: 'Create' as keyof typeof Icons,
                            color: '#42a5f5',
                        },
                        {
                            name: 'Yup',
                            description: 'Biblioteka do walidacji schematów obiektów',
                            iconName: 'FactCheck' as keyof typeof Icons,
                            color: '#4caf50',
                        },
                        {
                            name: 'Material UI',
                            description: 'Zestaw komponentów React zgodnych z wytycznymi Material Design',
                            iconName: 'Widgets' as keyof typeof Icons,
                            color: '#1976d2',
                        },
                        {
                            name: 'Axios',
                            description: 'Biblioteka do wykonywania żądań HTTP w przeglądarce i Node.js',
                            iconName: 'Http' as keyof typeof Icons,
                            color: '#80deea',
                        },
                    ]
                },
                {
                    subcategory: 'Serwer',
                    technologies: [
                        {
                            name: 'Java',
                            description: 'Język programowania obiektowego, używany głównie w aplikacjach enterprise',
                            iconName: 'Code' as keyof typeof Icons,
                            color: '#f89820',
                        },
                        {
                            name: 'Spring Framework',
                            description: 'Framework dla aplikacji Java, ułatwiający tworzenie aplikacji korporacyjnych',
                            iconName: 'Build' as keyof typeof Icons,
                            color: '#6db33f',
                        },
                        {
                            name: 'Hibernate',
                            description: 'ORM (Object-Relational Mapping) dla aplikacji Java',
                            iconName: 'Storage' as keyof typeof Icons,
                            color: '#59656f',
                        },
                        {
                            name: 'JUnit 5',
                            description: 'Biblioteka do testowania jednostkowego w języku Java',
                            iconName: 'FactCheck' as keyof typeof Icons,
                            color: '#25a162',
                        },
                        {
                            name: 'Testcontainers',
                            description: 'Biblioteka do tworzenia testów z użyciem kontenerów Docker',
                            iconName: 'LocalShipping' as keyof typeof Icons,
                            color: '#3caea3',
                        },
                        {
                            name: 'PostgreSQL serwer',
                            description: 'Otwartoźródłowy system zarządzania bazami danych',
                            iconName: 'Storage' as keyof typeof Icons,
                            color: '#336791',
                        },
                        {
                            name: 'PostgreSQL klient',
                            description: 'Klient do łączenia się z bazą danych PostgreSQL',
                            iconName: 'ConnectWithoutContact' as keyof typeof Icons,
                            color: '#336791',
                        },
                        {
                            name: 'REST Assured',
                            description: 'Biblioteka do testowania API REST w Java',
                            iconName: 'Http' as keyof typeof Icons,
                            color: '#f58025',
                        },
                        {
                            name: 'Swagger UI',
                            description: 'Narzędzie do dokumentacji i testowania interfejsów API REST',
                            iconName: 'Web' as keyof typeof Icons,
                            color: '#85c5ff',
                        },
                        {
                            name: 'JJWT',
                            description: 'Biblioteka Java do tworzenia i weryfikacji tokenów JWT',
                            iconName: 'VpnKey' as keyof typeof Icons,
                            color: '#3e4149',
                        },
                        {
                            name: 'Bouncy Castle',
                            description: 'Zestaw narzędzi kryptograficznych dla języka Java',
                            iconName: 'Security' as keyof typeof Icons,
                            color: '#ef8354',
                        },
                        {
                            name: 'Apache Commons Lang',
                            description: 'Biblioteka z narzędziami do pracy z językiem Java',
                            iconName: 'Translate' as keyof typeof Icons,
                            color: '#d05e34',
                        }
                    ],
                },
                {
                    subcategory: 'Narzędzia',
                    technologies: [
                        {
                            name: 'Docker',
                            description: 'Platforma do tworzenia, dystrybucji i uruchamiania kontenerów aplikacji',
                            iconName: 'LocalShipping' as keyof typeof Icons,
                            color: '#2496ed',
                        },
                        {
                            name: 'Apache Maven',
                            description: 'Narzędzie do zarządzania zależnościami i budowania projektów Java',
                            iconName: 'Settings' as keyof typeof Icons,
                            color: '#c71a36',
                        },
                        {
                            name: 'yarn',
                            description: 'Menadżer pakietów dla Node.js, szybki i niezawodny',
                            iconName: 'Build' as keyof typeof Icons,
                            color: '#2c8ebb',
                        },
                        {
                            name: 'nginx',
                            description: 'Serwer HTTP i reverse proxy, również serwer poczty',
                            iconName: 'SwapHoriz' as keyof typeof Icons,
                            color: '#009639',
                        },
                        {
                            name: 'GitHub Actions',
                            description: 'Automatyzacja CI/CD i innych zadań deweloperskich w GitHub',
                            iconName: 'DeviceHub' as keyof typeof Icons,
                            color: '#24292e',
                        },
                        {
                            name: 'SonarQube',
                            description: 'Platforma do statycznej analizy kodu i kontroli jakości',
                            iconName: 'BugReport' as keyof typeof Icons,
                            color: '#4e9a06',
                        },
                        {
                            name: 'ESLint',
                            description: 'Narzędzie do statycznej analizy kodu JavaScript/TypeScript',
                            iconName: 'Warning' as keyof typeof Icons,
                            color: '#4b32c3',
                        },
                        {
                            name: 'Prettier',
                            description: 'Automatyczne formatowanie kodu dla różnych języków programowania',
                            iconName: 'FormatAlignCenter' as keyof typeof Icons,
                            color: '#1a2b34',
                        }
                    ]
                }
            ],
        },
    ];

    const renderIcon = (iconName: string, color: string, fontSize: number, label: string) => {
        // @ts-ignore
        const Icon = Icons[iconName];
        return <Icon style={{color, fontSize}} aria-label={label}/>;
    };

    return (
        <>
            <Typography color="secondary" variant="h4" sx={{mb: 2}}>
                Technologie
            </Typography>
            <Divider sx={{mb: 3}}/>
            <TableContainer component={Paper}>
                <Table aria-label="Tabela technologii">
                    <caption style={{display: "none"}}>Lista technologii z podkategoriami, ikonami i opisami</caption>
                    <TableHead>
                        <TableRow>
                            <BoldTableCell component="th" scope="col">Podkategoria</BoldTableCell>
                            <BoldTableCell component="th" scope="col">Ikonka</BoldTableCell>
                            <BoldTableCell component="th" scope="col">Technologia</BoldTableCell>
                            <BoldTableCell component="th" scope="col">Opis</BoldTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.map(({items}) =>
                            items.map(({subcategory, technologies}) =>
                                technologies.map(({name, description, iconName, color}, index) => (
                                    <TableRow key={`${subcategory}-${name}`}>
                                        {index === 0 && (
                                            <BoldTableCell rowSpan={technologies.length} scope="row">
                                                <Typography variant="subtitle1">{subcategory}</Typography>
                                            </BoldTableCell>
                                        )}
                                        <IconTableCell>{renderIcon(iconName, color, 36, name)}</IconTableCell>
                                        <StyledTableCell>{name}</StyledTableCell>
                                        <StyledTableCell>{description}</StyledTableCell>
                                    </TableRow>
                                )),
                            ),
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            <Divider sx={{mb: 3}}/>

            <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
                Wymagania
            </Typography>

            <Divider sx={{ mb: 3 }} />

            <TableContainer component={Paper}>
                <Table aria-label="Tabela wymagań">
                    <TableHead>
                        <TableRow>
                            <TableCell style={{ fontWeight: 'bold', width: '50%' }}>Wymaganie</TableCell>
                            <TableCell style={{ fontWeight: 'bold', width: '30px', textAlign: 'center' }}>Spełnione?</TableCell>
                            <TableCell style={{ fontWeight: 'bold', width: '50%' }}>Opis</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {requirements.map((req, index) => (
                            <TableRow key={index}>
                                <TableCell style={{ width: '50%' }}>{req.requirement}</TableCell>
                                <TableCell style={{ textAlign: 'center' }}>{req.statusIcon}</TableCell>
                                <TableCell style={{ width: '50%' }}>{req.description}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Divider sx={{ mb: 3 }} />

            <Typography color="secondary" variant="h4" sx={{ mb: 2 }}>
                Dostępność
            </Typography>
            <Divider sx={{ mb: 3 }} />

            <Typography variant="body1">
                Zapewnienie dobrej dostępności dla niepełnosprawnych jest ograniczona z uwagi na rozmiar rozwiązania.
            </Typography>
            <Typography variant="body1">
                Niemniej jednak, przykładowo, aktualnie wyświetlana strona zawiera szereg technik zapewniania dostępności dla niepełnosprawnych:
            </Typography>
            <List>
                <ListItem>
                    <Typography variant="body1">
                        - Tabela technologii i wszystkie ikony posiada atrybut <b>aria-label</b> dla czytników ekranowych. Atrybuty opisują zawartość.
                    </Typography>
                </ListItem>
                <ListItem>
                    <Typography variant="body1">
                        - W nagłówkach tabelii wykorzystano semantyczne tagi HTML takie jak <b>caption</b> lub <b>th</b>.
                    </Typography>
                </ListItem>
                <ListItem>
                    <Typography variant="body1">
                        - Skorzystano z atrybutu <b>scope</b> do komerówek nagłówkowych wierszy.
                    </Typography>
                </ListItem>
            </List>
            <Typography variant="body1">
                Dostępność wyświetlanego komponentu została przetestowana za pomocą narzędzia <b>Lighthouse</b>. Można również dostępność przetestować czytnikami ekranu, np. NVDA (Windows) lub VoiceOver (MacOS).
            </Typography>

            <Divider sx={{ mb: 3 }} />

            <img
                src={accessibilityImg}
                alt="Accessibility tests illustration"
                style={{width: '100%', maxWidth: '900px', height: 'auto'}}
            />

            <Divider sx={{ mb: 3 }} />
        </>
    );
};

export default Help;
