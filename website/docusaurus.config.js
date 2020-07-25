module.exports = {
  title: 'Just FP',
  tagline: 'Just a small Functional Programming library',
  url: 'https://just-fp.kevinly.dev',
  baseUrl: '/',
  favicon: 'img/favicon.png',
  organizationName: 'Kevin-Lee',
  projectName: 'just-fp',
  themeConfig: {
    sidebarCollapsible: false,
    prism: {
      theme: require('prism-react-renderer/themes/nightOwl'),
      darkTheme: require('prism-react-renderer/themes/nightOwl'),
      additionalLanguages: ['scala'],
    },
    navbar: {
      title: 'Just FP',
      logo: {
        alt: 'Just FP Logo',
        src: 'img/just-fp-logo-32x32.png',
      },
      links: [
        {
          to: 'docs/',
          activeBasePath: 'docs',
          label: 'Docs',
          position: 'left',
        },
        {
          href: 'https://github.com/Kevin-Lee/just-fp',
          label: 'GitHub',
          position: 'right',
        },
      ],
    },
    footer: {
      style: 'dark',
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Getting Started',
              to: 'docs/',
            },
          ],
        },
        {
          title: 'More',
          items: [
            {
              label: 'GitHub',
              href: 'https://github.com/Kevin-Lee/just-fp',
            },
            {
              label: 'Blog',
              href: 'https://blog.kevinlee.io',
            },
            {
              label: 'Homepage',
              href: 'https://kevinlee.io',
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} just-fp, <a href="https://github.com/Kevin-Lee" target="_blank">Kevin Lee</a>. Built with Docusaurus.`,
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        docs: {
          path: '../generated-docs/target/mdoc/',
          // It is recommended to set document id as docs home page (`docs/` path).
          homePageId: 'getting-started',
          sidebarPath: require.resolve('./sidebars.js'),
        },
        blog: {
          showReadingTime: true,
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      },
    ],
  ],
};
